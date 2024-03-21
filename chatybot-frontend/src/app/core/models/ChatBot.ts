import { AllowedOrigins } from "./AllowedOrigins";

export interface ChatBot {
        id: string,
        name: string,
        logoUrl: string,
        initialMessage: string,
        headerColor: string,
        chatBackgroundColor: string,
        messageBackgroundColor: string,
        buttonBackgroundColor: string,
        botMessageColor: string,
        userMessageColor: string,
        textColor   : string,
        instructions: string,
        allowedOrigins: AllowedOrigins[],
        trained: boolean,
        temperature: number,
}