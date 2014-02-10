package competition.uu2013.common;

import ch.idsia.benchmark.mario.engine.sprites.Mario;
import ch.idsia.benchmark.mario.environments.Environment;

import java.util.ArrayList;

public class Action
{
    public static final int JUMP = 0;
    public static final int JUMP_SPEED = 1;
    public static final int RIGHT = 2;
    public static final int RIGHT_SPEED = 3;
    public static final int RIGHT_JUMP = 4;
    public static final int RIGHT_JUMP_SPEED = 5;
    public static final int LEFT = 6;
    public static final int LEFT_SPEED = 7;
    public static final int LEFT_JUMP = 8;
    public static final int LEFT_JUMP_SPEED = 9;
    public static final int DUCK = 10;
    public static final int UP = 11;
    public static final int SHOOT = 12;
    public static final int WAIT = -1;


    public static ArrayList<boolean[]> getPossibleActions(boolean mayJump, float marioX, float marioY, Map map)
    {
        ArrayList<boolean[]> actionList = new ArrayList<boolean[]>();

        if (mayJump)
        {
            actionList.add(createAction(Action.JUMP));
            actionList.add(createAction(Action.JUMP_SPEED));
            actionList.add(createAction(Action.LEFT_JUMP));
            actionList.add(createAction(Action.LEFT_JUMP_SPEED));
            actionList.add(createAction(Action.RIGHT_JUMP));
            actionList.add(createAction(Action.RIGHT_JUMP_SPEED));
        }
        if (map.getViewAt(marioX + Map.CELL_SIZE, marioY) == 0)
        {
            actionList.add(createAction(Action.RIGHT));
            actionList.add(createAction(Action.RIGHT_SPEED));
        }
        if (map.getViewAt(marioX - Map.CELL_SIZE, marioY) == 0)
        {
            actionList.add(createAction(Action.LEFT));
            actionList.add(createAction(Action.LEFT_SPEED));
        }

        actionList.add(createAction(Action.SHOOT));
        actionList.add(createAction(Action.WAIT));
        actionList.add(createAction(Action.DUCK));
        actionList.add(createAction(Action.UP));

        return actionList;
    }



    public static boolean[] createAction(int action)
    {
        boolean [] newAction = new boolean[Environment.numberOfKeys];
        switch (action)
        {
            case JUMP:
                newAction[Mario.KEY_JUMP] = true;
                newAction[Mario.KEY_LEFT] = false;
                newAction[Mario.KEY_RIGHT] = false;
                newAction[Mario.KEY_SPEED] = false;
                newAction[Mario.KEY_UP] = false;
                newAction[Mario.KEY_DOWN] = false;
                break;
            case JUMP_SPEED:
                newAction[Mario.KEY_JUMP] = true;
                newAction[Mario.KEY_LEFT] = false;
                newAction[Mario.KEY_RIGHT] = false;
                newAction[Mario.KEY_SPEED] = true;
                newAction[Mario.KEY_UP] = false;
                newAction[Mario.KEY_DOWN] = false;
                break;
            case RIGHT:
                newAction[Mario.KEY_JUMP] = false;
                newAction[Mario.KEY_LEFT] = false;
                newAction[Mario.KEY_RIGHT] = true;
                newAction[Mario.KEY_SPEED] = false;
                newAction[Mario.KEY_UP] = false;
                newAction[Mario.KEY_DOWN] = false;
                break;
            case RIGHT_SPEED:
                newAction[Mario.KEY_JUMP] = false;
                newAction[Mario.KEY_LEFT] = false;
                newAction[Mario.KEY_RIGHT] = true;
                newAction[Mario.KEY_SPEED] = true;
                newAction[Mario.KEY_UP] = false;
                newAction[Mario.KEY_DOWN] = false;
                break;
            case RIGHT_JUMP:
                newAction[Mario.KEY_JUMP] = true;
                newAction[Mario.KEY_LEFT] = false;
                newAction[Mario.KEY_RIGHT] = true;
                newAction[Mario.KEY_SPEED] = false;
                newAction[Mario.KEY_UP] = false;
                newAction[Mario.KEY_DOWN] = false;
                break;
            case RIGHT_JUMP_SPEED:
                newAction[Mario.KEY_JUMP] = true;
                newAction[Mario.KEY_LEFT] = false;
                newAction[Mario.KEY_RIGHT] = true;
                newAction[Mario.KEY_SPEED] = true;
                newAction[Mario.KEY_UP] = false;
                newAction[Mario.KEY_DOWN] = false;
                break;
            case LEFT:
                newAction[Mario.KEY_JUMP] = false;
                newAction[Mario.KEY_LEFT] = true;
                newAction[Mario.KEY_RIGHT] = false;
                newAction[Mario.KEY_SPEED] = false;
                newAction[Mario.KEY_UP] = false;
                newAction[Mario.KEY_DOWN] = false;
                break;
            case LEFT_SPEED:
                newAction[Mario.KEY_JUMP] = false;
                newAction[Mario.KEY_LEFT] = true;
                newAction[Mario.KEY_RIGHT] = false;
                newAction[Mario.KEY_SPEED] = true;
                newAction[Mario.KEY_UP] = false;
                newAction[Mario.KEY_DOWN] = false;
                break;
            case LEFT_JUMP:
                newAction[Mario.KEY_JUMP] = true;
                newAction[Mario.KEY_LEFT] = true;
                newAction[Mario.KEY_RIGHT] = false;
                newAction[Mario.KEY_SPEED] = false;
                newAction[Mario.KEY_UP] = false;
                newAction[Mario.KEY_DOWN] = false;
                break;
            case LEFT_JUMP_SPEED:
                newAction[Mario.KEY_JUMP] = true;
                newAction[Mario.KEY_LEFT] = true;
                newAction[Mario.KEY_RIGHT] = false;
                newAction[Mario.KEY_SPEED] = true;
                newAction[Mario.KEY_UP] = false;
                newAction[Mario.KEY_DOWN] = false;
                break;
            case DUCK:
                newAction[Mario.KEY_JUMP] = false;
                newAction[Mario.KEY_LEFT] = false;
                newAction[Mario.KEY_RIGHT] = false;
                newAction[Mario.KEY_SPEED] = false;
                newAction[Mario.KEY_UP] = false;
                newAction[Mario.KEY_DOWN] = true;
                break;
            case UP:
                newAction[Mario.KEY_JUMP] = false;
                newAction[Mario.KEY_LEFT] = false;
                newAction[Mario.KEY_RIGHT] = false;
                newAction[Mario.KEY_SPEED] = false;
                newAction[Mario.KEY_UP] = true;
                newAction[Mario.KEY_DOWN] = false;
                break;
            case SHOOT:
                newAction[Mario.KEY_JUMP] = false;
                newAction[Mario.KEY_LEFT] = false;
                newAction[Mario.KEY_RIGHT] = false;
                newAction[Mario.KEY_SPEED] = true;
                newAction[Mario.KEY_UP] = false;
                newAction[Mario.KEY_DOWN] = false;
                break;
            default:
                newAction[Mario.KEY_JUMP] = false;
                newAction[Mario.KEY_LEFT] = false;
                newAction[Mario.KEY_RIGHT] = false;
                newAction[Mario.KEY_SPEED] = false;
                newAction[Mario.KEY_UP] = false;
                newAction[Mario.KEY_DOWN] = false;
                break;

        }
    return newAction;
    }
}
