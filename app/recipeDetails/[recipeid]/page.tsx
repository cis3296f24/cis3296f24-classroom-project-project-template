"use client";
import { RecipeResult } from '@/types/RecipeResponseType';
import { fetchRecipeByID } from '@/utils/fetchRecipes';
import { useParams } from 'next/navigation';
import { useEffect, useState } from 'react';

export default function RecipeDetails() {
    const [recipe, setRecipe] = useState<RecipeResult | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const { recipeid } = useParams();

    useEffect(() => {
        const fetchRecipe = async () => {
            try {
                const recipeResult = await fetchRecipeByID(recipeid as string);
                if (!recipeResult) {
                    setError("Recipe not found.");
                } else {
                    setRecipe(recipeResult);
                }
            } catch (error) {
                console.error("Error fetching recipe:", error);
                setError("Failed to load recipe. Please try again later.");
            } finally {
                setLoading(false);
            }
        };

        if (recipeid) {
            fetchRecipe();
        }
    }, [recipeid]);

    if (loading) return <div>RECIPE IS LOADING!!</div>;
    if (error) return <div>{error}</div>;
    if (!recipe) return <div>No recipe found</div>;

    return (
        <div style={{ color: "white", fontSize: "40px", height: "20rem" }}>
            <img src={recipe.image} alt={recipe.label} />
            <h1>{recipe.label}</h1>
            <p>Source: {recipe.source}</p>
            <p>Diet Labels: {recipe.dietLabels.join(", ")}</p>
            <p>Health Labels: {recipe.healthLabels.join(", ")}</p>
        </div>
    );
}







//Katerina I commented this out because
// i couldn't figure out why there was an issue
//displaying the view recipe in the recipe cards
{/*}
"use client";
import { RecipeResult } from '@/types/RecipeResponseType';
import { fetchRecipeByID } from '@/utils/fetchRecipes';
import { useRouter, useParams } from 'next/navigation';
import { useEffect, useState } from 'react';

export default function RecipeDetails() {
    const [recipe, setRecipe] = useState<RecipeResult>()
    const [loading, setLoading] = useState(true)
    const router = useRouter();
    const recipeId = useParams().recipeid as string
    console.log(recipeId)
    useEffect(() => {
        const effect = async () => {
            const recipeResult = await fetchRecipeByID(recipeId);
            setRecipe(recipeResult)
            setLoading(false)
            }
    
        effect();
    }, []);

    //not sure about best way to set this up
    console.log(recipe)
    if (loading){return <div>RECIPE IS LOADING!!</div>}
    if (!recipe){return <div>No recipe Found</div>}
    return (
        <div style={{color:"white", fontSize:"40", height:"20rem"}}>
            <img src={recipe.image} alt="RECIPE IMAGE"></img>
            {recipe.label}

        </div>
    )
}
*/}