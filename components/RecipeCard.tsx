import { useRouter } from 'next/navigation';
import { RecipeResult } from "@/types/RecipeResponseType";
import styles from "@/app/styles.module.css";

const RecipeCard = ({
                        recipe,
                        onSave,
                        onAddToMealPlan}: {
    recipe: RecipeResult,
    onSave: (recipe: RecipeResult) => void,
    onAddToMealPlan?: (recipe: RecipeResult) => Promise<void>
}) => {
    const router = useRouter();
    const recipeId = recipe.uri?.split("recipe_")[1]

    const viewRecipeDetails = () => {
        router.push(`/recipeDetails/${recipeId}?label=${encodeURIComponent(recipe.label)}`);
    };

    return (
        <div className={styles.recipeCard}>
            <img src={recipe.image} alt={recipe.label}/>
            <h3>{recipe.label || recipe.title}</h3>
            <button className={styles.viewButton} onClick={viewRecipeDetails}>
                View Recipe
            </button>
            <button className={styles.saveButton} onClick={() => onSave(recipe)}>
                Save Recipe
            </button>
            {onAddToMealPlan && (
                <button className={styles.saveButton} onClick={() => onAddToMealPlan(recipe)}>
                    Add to Meal Planner
                </button>
            )}
        </div>
    );
};


export default RecipeCard;