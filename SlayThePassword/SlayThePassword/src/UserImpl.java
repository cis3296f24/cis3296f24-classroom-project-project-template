public class UserImpl implements User{
    public int health;
    public int maxHealth;

    public UserImpl(){
        health = 5;
        maxHealth = 5;
    }

    @Override
    public void losehp(int value){
        health -= value;
        if (health < 0)
            health = 0;
    }

    @Override
    public void restorehp(int value){
        health += value;
        if (health > maxHealth)
            health = maxHealth;
    }

    @Override
    public int getHealth()
    {
        return health;
    }
}
