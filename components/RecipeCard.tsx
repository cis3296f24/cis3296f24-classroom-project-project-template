import { useState } from 'react';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import { addRecipeToMealPlan } from '@/utils/addRecipeToMealPlan';
import { RecipeResult } from '@/types/RecipeResponseType';
import { useUser } from '@clerk/nextjs';
import styles from './RecipeCard.module.css';
import Link from 'next/link';

interface RecipeCardProps {
    recipe: RecipeResult;
    onSave: (recipe: RecipeResult) => void;
}

const RecipeCard = ({ recipe, onSave }: RecipeCardProps) => {
    const { user } = useUser();
    const userId = user?.id;

    const [showDatePicker, setShowDatePicker] = useState(false);
    const [selectedDate, setSelectedDate] = useState<Date | null>(null);

    const handleAddToMealPlan = async () => {
        if (selectedDate && recipe.uri && userId) {
            await addRecipeToMealPlan(recipe.uri, recipe.label, selectedDate, userId);

            setShowDatePicker(false);
            alert("Recipe added to meal plan!");
        } else {
            alert("Please select a date and ensure you are logged in.");
        }
    };

    return (
        <div className={styles.recipeCard}>
            <div className={styles.recipeHeader}>{recipe.title}</div>
            <img src={recipe.image} alt={recipe.label} className={styles.recipeImage} />
            <p>Source: {recipe.source}</p>
            <div className={styles.buttonGroup}>
                <button className={styles.viewButton}>
                    <Link href={`/recipeDetails/${recipe.uri}`} className={styles.viewButton}>View Recipe</Link>
                </button>
                <button className={styles.saveButton} onClick={() => onSave(recipe)}>
                    Save Recipe
                </button>
                <button
                    className={styles.mealPlanButton}
                    onClick={() => setShowDatePicker(!showDatePicker)}
                >
                    Add to Meal Plan?
                </button>
            </div>
            {showDatePicker && (
                <div className={styles.datePickerContainer}>
                    <DatePicker
                        selected={selectedDate}
                        onChange={(date: Date | null) => setSelectedDate(date)}
                    />
                    <button onClick={handleAddToMealPlan} className={styles.confirmButton}>
                        Confirm
                    </button>
                </div>
            )}
        </div>
    );
};

export default RecipeCard;
