export const addRecipeToMealPlan = async (recipeId: string, recipeName: string, date: Date, userId: string) => {
    const response = await fetch('/api/mealPlans', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ recipeId, recipeName, date, userId }),
    });

    if (!response.ok) {
        console.error("Failed to add recipe to meal plan", await response.json());
    }

    return response;
};
