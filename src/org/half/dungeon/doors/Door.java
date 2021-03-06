package org.half.dungeon.doors;

import org.half.dungeon.rooms.Room;
import org.half.utils.Misc;
import org.powerbot.game.api.wrappers.node.SceneObject;

import java.awt.*;

public abstract class Door
{
    private static final Color COLOR_CLOSED = new Color(255, 0, 0, 192);
    private static final Color COLOR_OPENED = new Color(0, 128, 0, 192);
    private static final Color COLOR_CAN_BE_OPENED = new Color(255, 128, 64, 192);

    protected final int _position;
    protected final SceneObject _object;
    protected Room _destinationRoom;
    protected boolean _opened = false;

    /**
     * Creates a door object.
     *
     * @param position The position of this door in the room. (0 = North, 1 = East, 2 = South, 3 = West)
     * @param object   The scene object associated with this door.
     */
    protected Door(final int position, final SceneObject object)
    {
        _position = position;
        _object = object;
    }

    /**
     * Gets the position in the room where this door is.
     *
     * @return The position of this door in the room. (0 = North, 1 = East, 2 = South, 3 = West)
     */
    public int getPosition()
    {
        return _position;
    }

    /**
     * Gets the position in the room where this door is.
     *
     * @return The compass position of this door in the room.
     */
    public String getCompassPosition()
    {
        switch (_position)
        {
            case 0:
                return "North";
            case 1:
                return "East";
            case 2:
                return "South";
            case 3:
                return "West";
        }
        return null;
    }

    /**
     * Gets this door scene object.
     */
    public SceneObject getObject()
    {
        return _object;
    }

    /**
     * Gets the room that this door leads to.
     */
    public Room getDestinationRoom()
    {
        return _destinationRoom;
    }

    /**
     * Sets the room that this door leads to.
     */
    public void setDestinationRoom(Room destinationRoom)
    {
        _destinationRoom = destinationRoom;
    }

    /**
     * Checks if the door is open.
     *
     * @return true if the door is open; false otherwise.
     */
    public boolean isOpen()
    {
        return _opened;
    }

    /**
     * Opens this door.
     */
    public void open()
    {
        _opened = true;
    }

    /**
     * Gets this door color for painting.
     *
     * @return The color of this door.
     */
    public Color color()
    {
        if (_opened)
        {
            return COLOR_OPENED;
        }

        if (canBeOpened())
        {
            return COLOR_CAN_BE_OPENED;
        }

        return COLOR_CLOSED;
    }

    /**
     * Checks if this door can be opened.
     *
     * @return true if this door can be opened; false otherwise;
     */
    public abstract boolean canBeOpened();

    @Override
    public String toString()
    {
        return getCompassPosition() + " Door";
    }

    /**
     * Creates a new door from an existing scene object.
     *
     * @param position The position of this door in the room. (0 = North, 1 = East, 2 = South, 3 = West)
     * @param object   The scene object associated with this door.
     * @return A corresponding door object.
     */
    public static Door createFromObject(final int position, final SceneObject object)
    {
        if (object == null)
        {
            return null;
        }
        else if (Misc.inArray(NormalDoor.OBJECT_DOOR_NORMAL, object.getId()))
        {
            return new NormalDoor(position, object);
        }
        else if (Misc.inArray(GuardianDoor.OBJECT_DOOR_GUARDIAN, object.getId()))
        {
            return new GuardianDoor(position, object);
        }
        else if (Misc.inArray(BossDoor.OBJECT_DOOR_BOSS, object.getId()))
        {
            return new BossDoor(position, object);
        }
        else if (Misc.inArray(KeyDoor.OBJECT_DOOR_KEY, object.getId()))
        {
            return new KeyDoor(position, object);
        }
        else if (Misc.inArray(SkillDoor.OBJECT_DOOR_SKILL, object.getId()))
        {
            return new SkillDoor(position, object);
        }
        else if (Misc.inArray(PuzzleDoor.OBJECT_DOOR_PUZZLE, object.getId()))
        {
            return new PuzzleDoor(position, object);
        }

        return null;
    }

    /**
     * Checks if a scene object is a door.
     *
     * @param object Scene object to check.
     * @return True if scene object is a door. False otherwise.
     */
    public static boolean objectIsDoor(final SceneObject object)
    {
        return object != null
                && (Misc.inArray(NormalDoor.OBJECT_DOOR_NORMAL, object.getId())
                || Misc.inArray(GuardianDoor.OBJECT_DOOR_GUARDIAN, object.getId())
                || Misc.inArray(BossDoor.OBJECT_DOOR_BOSS, object.getId())
                || Misc.inArray(KeyDoor.OBJECT_DOOR_KEY, object.getId())
                || Misc.inArray(SkillDoor.OBJECT_DOOR_SKILL, object.getId())
                || Misc.inArray(PuzzleDoor.OBJECT_DOOR_PUZZLE, object.getId()));
    }
}
