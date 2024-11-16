"use client";

import { useState, useEffect } from 'react';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import { fetchMealPlans } from '@/utils/fetchMealPlans';
import { useUser } from '@clerk/nextjs';
import RecipeCard from '@/components/RecipeCard';
import { RecipeResult } from '@/types/RecipeResponseType';

// Define the MealPlan interface based on your backend schema
interface MealPlan {
    recipeId: string;
    recipeName: string;
    image?: string; // Optional if not always provided
    source?: string; // Optional if not always provided
    date: string; // Stored as ISO string in the database
    userId: string;
}

const MealPlanner = () => {
    const { user } = useUser();
    const userId = user?.id;

    const [selectedDate, setSelectedDate] = useState<Date>(new Date());
    const [mealPlans, setMealPlans] = useState<MealPlan[]>([]);

    useEffect(() => {
        if (userId) {
            const fetchPlans = async () => {
                try {
                    const plans = await fetchMealPlans(userId);
                    setMealPlans(plans);
                } catch (error) {
                    console.error("Failed to fetch meal plans:", error);
                }
            };
            fetchPlans();
        }
    }, [userId]);

    const getMealsForDate = (date: Date) => {
        return mealPlans.filter((plan) => {
            const planDate = new Date(plan.date);
            return (
                !isNaN(planDate.getTime()) && planDate.toDateString() === date.toDateString()
            );
        });
    };

    return (
        <div>
            <h1>Meal Planner</h1>
            <Calendar
                onChange={(date) => {
                    if (date instanceof Date) setSelectedDate(date);
                }}
                value={selectedDate}
            />
            <div>
                <h2>Meals for {selectedDate.toDateString()}</h2>
                <div>
                    {getMealsForDate(selectedDate).map((meal, index) => {
                        const recipe: Partial<RecipeResult> = {  // Use Partial type to only require a few fields
                            label: meal.recipeName,
                            image: meal.image || '/placeholder-image.png',
                        };

                        return (
                            <RecipeCard
                                key={index}
                                recipe={recipe}
                                onView={() => console.log(`Viewing recipe: ${recipe.label}`)}
                            />
                        );
                    })}
                </div>
            </div>
        </div>
    );
};

export default MealPlanner;