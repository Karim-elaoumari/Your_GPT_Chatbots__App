import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserRegister } from 'src/app/core/models/UserRegister';
import { UserResponse } from 'src/app/core/models/UserResponse';
import { AuthService } from 'src/app/core/services/AuthService';
import { TokenStorageService } from 'src/app/core/services/TokenStorageService';
import { RegisterValidation } from 'src/app/core/validators/RegisterValidation';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  error: string = '';
  success: string = '';
  register_loading: boolean = false;
  user_register:UserRegister = {} as UserRegister;
  register_validation:{ [key: string]: string } = {};
  google_url: string = '';

  constructor(private authService:AuthService,
    private registerValidation:RegisterValidation,
    private  router:Router,
    private tokenStorageService:TokenStorageService
    ) { }
  ngOnInit(): void {
    console.log('registerComponent');
    this.getGoogleUrl();
  }

  register() {
    console.log(this.user_register);
    this.register_validation = this.registerValidation.validate(this.user_register);
    if(Object.keys(this.register_validation).length === 0){
      this.register_loading = true;
      this.authService.register(this.user_register).subscribe(
        (response) => {
          if(response.statusCode === 200){
            console.log(response);
            this.tokenStorageService.saveToken(response.data.token.access_token);
            this.tokenStorageService.saveRefreshToken(response.data.token.refresh_token);
            this.tokenStorageService.saveUser(response.data as UserResponse);
            this.router.navigate(['/home']);
          }else{
            this.register_loading = false;
            this.error = 'Error while Logging test';
          }
        },
        (error) => {
          this.register_loading = false;
          this.error = 'Error while registering Try again';
          this.success = '';
        }
      );
    }
  }
  getGoogleUrl(){
    return this.authService.getGoogleLoginUrl().subscribe(
      (response) => {
        console.log(response);
        this.google_url = response.authURL;
      },
      (error) => {
        console.log(error);
      }
    );
  }
  loginWithGoogle(){
    if(this.google_url !== ''){
    window.location.href = this.google_url;
    }
  }

}
