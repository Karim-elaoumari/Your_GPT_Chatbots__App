import { Injectable } from "@angular/core";
import { UserLogin } from "../models/UserLogin";

@Injectable(
    {
        providedIn: 'root'
    }
)
export class LoginValidation{
    validate(userLogin:UserLogin):{ [key: string]: string } {
        const errors: { [key: string]: string } = {};
        if (userLogin.email == null || userLogin.email.length === 0) {
            errors["email"] = "Email is required";
          } else if (!this.isValidEmail(userLogin.email)) {
            errors["email"] = "Invalid Email";
          }
      
        if (userLogin.password == null || userLogin.password.length === 0) {
        errors["password"] = "Password is required";
        } else if (!this.isValidPassword(userLogin.password)) {
        errors["password"] =
            "Password should contain number, uppercase, lowercase, special character, min 8 char";
        }
        return errors;

    }
    private isValidEmail(email: string): boolean {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
      }
    
    private isValidPassword(password: string): boolean {
    const passwordRegex = /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\S+$).{8,}$/;
    return passwordRegex.test(password);
    }
}