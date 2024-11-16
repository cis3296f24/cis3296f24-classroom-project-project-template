import User from "@/libs/models/user.model";
import { connect } from "@/libs/db";

interface UserPayload {
    chefID: string;
    firstName: string;
    lastName: string;
    email: string;
    username: string;
}

export async function CreateUser(user: UserPayload) {
    try {
        await connect();
        const newUser = await User.create(user);
        return JSON.parse(JSON.stringify(newUser));
    } catch (error) {
        console.error("Error creating user:", error);
        throw new Error("User creation failed");
    }
}
