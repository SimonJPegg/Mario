package competition.uu2013.common.Sprites;

import ch.idsia.benchmark.mario.engine.GlobalOptions;
import ch.idsia.benchmark.mario.engine.sprites.Mario;
import ch.idsia.benchmark.mario.engine.sprites.Sprite;
import ch.idsia.benchmark.mario.environments.Environment;
import competition.uu2013.common.Map;
import competition.uu2013.common.WorldSim;

public class MarioSim  extends SpriteSim
{

    public float x,y,xa,ya,lastX,lastY;          // max speed = 4.85 walking, 9.7 running
    private boolean big = true;
    private boolean fire = true;
    private int invulnerableTime = 0;
    private int jumpTime;
    private int facing = 1;
    private boolean onGround = false;
    private boolean wasOnGround = false;
    private boolean mayJump = false;
    private boolean sliding = false;
    private boolean dead = false;
    private float xJumpSpeed = 0;
    private float yJumpSpeed = 0;
    private boolean keys_last[];
    private boolean keys[];
    private WorldSim worldSim;
    public EnemySim carried;
    private float oldX;
    private float oldY;
    private float oldYa;
    private boolean oldWasOnGround;
    private boolean oldIsOnGround;
    private Mario marioActual;
    private boolean ableToShoot = true;


    public static final int KEY_LEFT = 0;
    public static final int KEY_RIGHT = 1;
    public static final int KEY_DOWN = 2;
    public static final int KEY_JUMP = 3;
    public static final int KEY_SPEED = 4;
    public static final int KEY_UP = 5;



    public MarioSim(float _x, float _y, float _xa, float _ya)
    {
        lastX = x = _x;
        lastY = y = _y;
        xa = _xa;
        ya = _ya;
        keys_last = new boolean[Environment.numberOfKeys];
        this.marioActual = GlobalOptions.mario;
    }

    public MarioSim clone()
    {
        MarioSim m = new MarioSim(this.x, this.y, this.xa, this.ya);
        return m;
    }


    @Override
    public float getX()
    {
        return this.x;
    }

    @Override
    public float getY()
    {
        return this.y;
    }
    public void setWorldSim(WorldSim _sim)
    {
        this.worldSim = _sim;
    }


    public float getLastX()
    {
        return this.lastX;
    }

    public float getLastY()
    {
        return this.lastY;
    }

    public int height()
    {
        return big ? 24 : 12;
    }

    public void move()
    {
        keys_last = keys;
        boolean ducking = false;
        float sideWaysSpeed = keys[KEY_SPEED]  ? 1.2f : 0.6f;
        this.oldX = x;
        this.oldY = y;
        this.oldYa = this.ya;
        this.oldWasOnGround = wasOnGround;
        this.oldIsOnGround = onGround;

        if (xa > 2)
        {
            facing = 1;
        }
        if (xa < -2)
        {
            facing = -1;
        }


        if (keys[KEY_JUMP] || (jumpTime < 0 && !onGround && !sliding))
        {
            if (jumpTime < 0)
            {
                xa = xJumpSpeed;
                ya = -jumpTime * yJumpSpeed;
                jumpTime++;
            } else if (onGround && mayJump)
            {
                xJumpSpeed = 0;
                yJumpSpeed = -1.9f;
                jumpTime = 7;
                ya = jumpTime * yJumpSpeed;
                onGround = false;
                sliding = false;
            } else if (sliding && mayJump)
            {
                xJumpSpeed = -facing * 6.0f;
                yJumpSpeed = -2.0f;
                jumpTime = -6;
                xa = xJumpSpeed;
                ya = -jumpTime * yJumpSpeed;
                onGround = false;
                sliding = false;
                facing = -facing;
            } else if (jumpTime > 0)
            {
                xa += xJumpSpeed;
                ya = jumpTime * yJumpSpeed;
                jumpTime--;
            }
        } else
        {
            jumpTime = 0;
        }

        if (keys[KEY_LEFT] && !ducking)
        {
            if (facing == 1) sliding = false;
            xa -= sideWaysSpeed;
            if (jumpTime >= 0) facing = -1;
        }

        if (keys[KEY_RIGHT] && !ducking)
        {
            if (facing == -1) sliding = false;
            xa += sideWaysSpeed;
            if (jumpTime >= 0) facing = 1;
        }

        if ((!keys[KEY_LEFT] && !keys[KEY_RIGHT]) || ducking || ya < 0 || onGround)
        {
            sliding = false;
        }


        if (keys[KEY_SPEED] && ableToShoot && this.fire && worldSim.numFireBalls() < 2)
        {
            worldSim.addFireball(new FireBallSim(x + facing * 6, y - 20, Sprite.KIND_FIREBALL , facing));
        }

        ableToShoot = (worldSim.numFireBalls() < 2);

        mayJump = (onGround || sliding) && !keys[KEY_JUMP];


        if (Math.abs(xa) < 0.5f)
        {
            xa = 0;
        }

        if (sliding)
        {
            ya *= 0.5f;
        }

        onGround = false;
        move(xa, 0);
        move(0, ya);

        if (x < 0)
        {
            x = 0;
            xa = 0;
        }

        ya *= AIR_INERTIA;
        xa *= GROUND_INERTIA;

        if (!onGround)
        {
            ya += 3;
        }
    }

    private boolean move(float xa, float ya)
    {
        while (xa > 8) {
            if (!move(8, 0))
            {
                return false;
            }
            xa -= 8;
        }
        while (xa < -8) {
            if (!move(-8, 0)) return false;
            xa += 8;
        }
        while (ya > 8) {
            if (!move(0, 8)) return false;
            ya -= 8;
        }
        while (ya < -8) {
            if (!move(0, -8)) return false;
            ya += 8;
        }

        boolean collide = false;
        int width = 4;
        int height = big ? 24 : 12;
        if (ya > 0)
        {
            if (isBlocking(x + xa - width, y + ya, xa, 0)) collide = true;
            else if (isBlocking(x + xa + width, y + ya, xa, 0)) collide = true;
            else if (isBlocking(x + xa - width, y + ya + 1, xa, ya)) collide = true;
            else if (isBlocking(x + xa + width, y + ya + 1, xa, ya)) collide = true;
        }
        if (ya < 0)
        {
            if (isBlocking(x + xa, y + ya - height, xa, ya)) collide = true;
            else if (collide || isBlocking(x + xa - width, y + ya - height, xa, ya)) collide = true;
            else if (collide || isBlocking(x + xa + width, y + ya - height, xa, ya)) collide = true;
        }
        if (xa > 0)
        {
            sliding = true;
            if (isBlocking(x + xa + width, y + ya - height, xa, ya)) collide = true;
            else sliding = false;
            if (isBlocking(x + xa + width, y + ya - height / 2, xa, ya)) collide = true;
            else sliding = false;
            if (isBlocking(x + xa + width, y + ya, xa, ya)) collide = true;
            else sliding = false;
        }
        if (xa < 0)
        {
            sliding = true;
            if (isBlocking(x + xa - width, y + ya - height, xa, ya)) collide = true;
            else sliding = false;
            if (isBlocking(x + xa - width, y + ya - height / 2, xa, ya)) collide = true;
            else sliding = false;
            if (isBlocking(x + xa - width, y + ya, xa, ya)) collide = true;
            else sliding = false;
        }

        if (collide)
        {
            if (xa < 0) {
                x = (int) ((x - width) / 16) * 16 + width;
                this.xa = 0;
            } else if (xa > 0) {
                x = (int) ((x + width) / 16 + 1) * 16 - width - 1;
                this.xa = 0;
            }

            if (ya < 0) {
                y = (int) ((y - height) / 16) * 16 + height;
                jumpTime = 0;
                this.ya = 0;
            } else if (ya > 0) {
                y = (int) ((y - 1) / 16 + 1) * 16 - 1;
                onGround = true;
            }
            return false;
        }
        else
        {
            x += xa;
            y += ya;
            return true;
        }
    }

    private boolean isBlocking(float _x, float _y, float xa, float ya)
    {
        int x = (int) (_x / 16); // block's quantized pos
        int y = (int) (_y / 16);

        int Mx = (int) (this.x / 16); // reddit's quantized pos
        int My = (int) (this.y / 16);
        if (x == Mx && y == My) return false;

        boolean blocking = Map.isBlocking(x,y,xa,ya);

        byte block = Map.getBlock(x,y);

        if(block == 34) { // coin


            Map.setBlock(x,y,(byte)0);
            return false;
        }

        //if (blocking && ya < 0)
            //ws = ws.bump(x, y, big);

        if (blocking && ya < 0)
        {
            worldSim.bump(x, y, big);
        }

        return blocking;
    }

    public void stomp(SpriteSim enemy)
    {
        System.out.println(" PREDICTED STOMP!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("T: " + enemy.type + " X: " + enemy.x + " Y: " + enemy.y + "Height: " + enemy.height());

        float targetY = enemy.y - enemy.height() / 2;
        move(0, targetY - y);

        xJumpSpeed = 0;
        yJumpSpeed = -1.9f;
        jumpTime = 8;
        ya = jumpTime * yJumpSpeed;
        onGround = false;
        sliding = false;
        invulnerableTime = 1;
        System.out.println("XA: " + xa + " YA: " + ya );
    }

    public void stomp(boolean [] keys, final ShellSim shell)
    {
        if (keys[KEY_SPEED] && shell.facing == 0)
        {
            carried = shell;
            shell.carried = true;
        } else
        {
            float targetY = shell.y - shell.height / 2;
            move(0, targetY - y);

            xJumpSpeed = 0;
            yJumpSpeed = -1.9f;
            jumpTime = 8;
            ya = jumpTime * yJumpSpeed;
            onGround = false;
            sliding = false;
            invulnerableTime = 1;
        }
    }

    public void getHurt()
    {
        if (invulnerableTime > 0) return;

        if (big) {
            if (fire) {
                fire = false;
            } else {
                big = false;
            }
            invulnerableTime = 32;
        } else {
            dead = true;
        }
        dead = true;
    }

    public void syncLocation(float _x, float _y, boolean _mayJump, boolean _onGround, boolean _fire, boolean _big)
    {
        if ((_x != x)|| (_y !=y))
        {
            x = _x;
            y = _y;
            xa = (x - lastX) * GROUND_INERTIA;
            ya = (y - lastY) * AIR_INERTIA + 3.0F;
        }

        this.mayJump = _mayJump;
        wasOnGround = onGround;
        this.onGround = _onGround;
        this.big = _big;
        this.fire = _fire;

    }

    public float getYa()
    {
        return this.ya;
    }

    public boolean isOnGround()
    {
        return onGround;
    }

    public boolean wasOnGround()
    {
        return wasOnGround;
    }

    public void setMode(boolean _big, boolean _fire)
    {
        this.big = _big;
        this.fire = _fire;
    }

    public void setKeys(boolean[] _keys, float accurateX, float accurateY)
    {
        keys = _keys;
        lastX = accurateX;
        lastY = accurateY;
    }

    public float getOldX() {
        return oldX;
    }

    public float getOldY() {
        return oldY;
    }

    public float getOldYa() {
        return oldYa;
    }

    public boolean oldWasOnGround()
    {
        return this.oldWasOnGround;
    }

    public boolean oldIsOnGroundOld()
    {
        return this.oldIsOnGround;
    }

    public Object getXA() {
        return xa;
    }
}