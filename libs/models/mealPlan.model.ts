import mongoose, { Document, Schema } from 'mongoose';

interface MealPlanDocument extends Document {
    recipeId: string;
    recipeName: string;
    image: string; // Add image if needed
    source: string; // Add source if needed
    date: Date;
    userId: string;
}

const mealPlanSchema = new Schema<MealPlanDocument>({
    recipeId: { type: String, required: true },
    recipeName: { type: String, required: true },
    image: { type: String }, // Optional if not always needed
    source: { type: String }, // Optional if not always needed
    date: { type: Date, required: true },
    userId: { type: String, required: true },
});

const MealPlan = mongoose.models.MealPlan || mongoose.model<MealPlanDocument>('MealPlan', mealPlanSchema);

export default MealPlan;
