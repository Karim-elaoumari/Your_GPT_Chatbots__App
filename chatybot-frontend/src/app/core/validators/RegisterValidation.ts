
import { Injectable } from "@angular/core";
import { UserRegister } from "../models/UserRegister";
@Injectable(
    {
        providedIn: 'root'
    }
)
export class RegisterValidation{
    validate(userRegister: UserRegister): { [key: string]: string } {
        const errors: { [key: string]: string } = {};
    
        if (userRegister.firstName == null || userRegister.firstName.length === 0) {
          errors["firstName"] = "First Name is required";
        }
    
        if (userRegister.lastName == null || userRegister.lastName.length === 0) {
          errors["lastName"] = "Last Name is required";
        }
    
        if (userRegister.email == null || userRegister.email.length === 0) {
          errors["email"] = "Email is required";
        } else if (!this.isValidEmail(userRegister.email)) {
          errors["email"] = "Invalid Email";
        }
    
        if (userRegister.password == null || userRegister.password.length === 0) {
          errors["password"] = "Password is required";
        } else if (!this.isValidPassword(userRegister.password)) {
          errors["password"] =
            "Password should contain number, uppercase, lowercase, special character, min 8 char";
        }
    
        if (userRegister.password !== userRegister.confirmPassword) {
          errors["confirmPassword"] = "Password and Confirm Password are not the same";
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