import { Component } from '@angular/core';
import { UserLogin } from 'src/app/core/models/UserLogin';
import { AuthService } from 'src/app/core/services/AuthService';
import { TokenStorageService } from 'src/app/core/services/TokenStorageService';
import { LoginValidation } from 'src/app/core/validators/LoginValidation';
import {  ActivatedRoute, Router } from '@angular/router';
import { UserResponse } from 'src/app/core/models/UserResponse';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  user_login:UserLogin = {} as UserLogin;
  error: string = '';
  success: string = '';
  login_loading: boolean = false;
  login_validation:{ [key: string]: string } = {};
  google_url: string = '';
  code: string = this.route.snapshot.queryParams['code'];

  constructor(private authService:AuthService,
    private loginValidation:LoginValidation,
    private  router:Router,
    private route:ActivatedRoute,
    private tokenStorageService:TokenStorageService
    ) { }
  ngOnInit(): void {
    this.getGoogleUrl();
    this.authenticateWithGoogle();
  }
  login() {
    console.log(this.user_login);
    this.login_validation = this.loginValidation.validate(this.user_login);
    if(Object.keys(this.login_validation).length === 0){
        this.login_loading = true;
        this.authService.login(this.user_login.email, this.user_login.password).subscribe(
          (response) => {
            this.error = '';
            if(response.statusCode === 200){
              console.log(response);
              this.tokenStorageService.saveToken(response.data.token.access_token);
              this.tokenStorageService.saveRefreshToken(response.data.token.refresh_token);
              this.tokenStorageService.saveUser(response.data as UserResponse);
              this.router.navigate(['/home']);
            }else{
              this.login_loading = false;
              this.error = 'Error while Logging';
            }
          },
          (error) => {
            this.success = '';
            this.login_loading = false;
            this.error = 'Error while Logging Try again';
          }
        );
    }

  }
  loginWithGoogle(){
    if(this.google_url !== ''){
    window.location.href = this.google_url;
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
  authenticateWithGoogle(){
    if(this.code!==undefined && this.code!==null && this.code!==''){
      this.authService.loginWithGoogle(this.code).subscribe(
        data => {
          this.tokenStorageService.saveToken(data.token.access_token);
          this.tokenStorageService.saveRefreshToken(data.token.refresh_token);
          this.tokenStorageService.saveUser(data as UserResponse);
          this.router.navigate(['/']);
        },
        error => {
          this.error = 'Error while Logging in with Google';
        }
      )
    }
  }
  

}
