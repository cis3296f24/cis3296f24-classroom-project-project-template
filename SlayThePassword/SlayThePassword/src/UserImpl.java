

public class UserImpl implements User{
    public int health;
    public int maxHealth;

    public UserImpl(){
        health = 5;
        maxHealth = 5;
    }

    @Override
    public void losehp(Integer value){
        if(value == null){
            health -= 1;
        }
        else{
            health -= value;
        }
    }

    @Override
    public void restorehp(Integer value){
        if(value == null){
            health = maxHealth;
        }
        else{
            if(health + value >= maxHealth){
                restorehp(maxHealth - health);
            }else{
                health += value;
            }
        }
    }

    @Override
    public int getHealth()
    {
        return health;
    }
}
