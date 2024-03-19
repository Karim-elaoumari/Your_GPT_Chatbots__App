import { Injectable } from "@angular/core";
import { ChatBot } from "../models/ChatBot";

@Injectable(
    {
        providedIn: 'root'
    }
)
export class ChatBotUpdateValidation {
    validate(chatBotUpdate:ChatBot):{ [key: string]: string }{ 
        const errors: { [key: string]: string } = {};
        if (chatBotUpdate.name == null || chatBotUpdate.name.length === 0) {
            errors["name"] = "Name is required";
        }
        if (chatBotUpdate.logoUrl == null ) {
            errors["logoUrl"] = "Logo Url is required";
        }
        else if (!this.isLogoUrlValid(chatBotUpdate.logoUrl)) {
            errors["logoUrl"] = "Invalid Logo Url";
        }
        if (chatBotUpdate.initialMessage == null || chatBotUpdate.initialMessage.length === 0) {
            errors["initialMessage"] = "Initial Message is required";
        }
        if (chatBotUpdate.instructions == null || chatBotUpdate.instructions.length === 0) {
            errors["instructions"] = "Instructions is required";
        }
        return errors;


    }
    private isLogoUrlValid(logoUrl: string): boolean {
        const logoUrlRegex = /^(http|https):\/\//;
        return logoUrlRegex.test(logoUrl);
    }

    
}