import { subscription } from "./Subscription";

export interface UserResponseInfo {
    id: number;
    firstName: string;
    lastName: string;
    email: string;
    username: string;
    picture: string;
    provider: string;
    subscription: subscription;
    role: string;
}