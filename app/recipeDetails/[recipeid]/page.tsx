"use client";
import { RecipeResult } from '@/types/RecipeResponseType';
import { fetchRecipeByID } from '@/utils/fetchRecipes';
import { useParams } from 'next/navigation';
import { useEffect, useState } from 'react';
import YouTubeVideoFetcher from '@/components/YouTubeVideoFetcher';
import Image from "next/image";

export default function RecipeDetails() {
    const [recipe, setRecipe] = useState<RecipeResult>()
    const [loading, setLoading] = useState(true)
    const recipeId = useParams().recipeid as string

    // Fetch Recipe Details
    useEffect(() => {
        const fetchRecipe = async () => {
            try {
                const recipeResult = await fetchRecipeByID(recipeId);
                setRecipe(recipeResult);
            } catch (error) {
                console.error('Error fetching recipe:', error);
            } finally {
                setLoading(false);
            }
        };
        fetchRecipe();
    }, [recipeId]);


    //useEffect(() => {
      //  const effect = async () => {
        //    const recipeResult = await fetchRecipeByID(recipeId);
          //  setRecipe(recipeResult)
            //setLoading(false)
            //}
    //
      //  effect();
    //}, []);

    //not sure about best way to set this up
    //console.log(recipe)
    if (loading){return <div>RECIPE IS LOADING!!</div>}
    if (!recipe){return <div>No recipe Found</div>}

    return (
        <div style={{ color: "white", fontSize: "40", height: "20rem" }}>
            <img src={recipe.image} alt="RECIPE IMAGE" />
            <h1>{recipe.label}</h1>
            {/* Use the reusable YouTubeVideoFetcher component */}
            <YouTubeVideoFetcher query={recipe.label} />
        </div>
    );
}
