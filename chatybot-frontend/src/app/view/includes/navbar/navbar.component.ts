import { Component} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserResponseInfo } from 'src/app/core/models/UserResponseInfo';
import { TokenStorageService } from 'src/app/core/services/TokenStorageService';
@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {
  constructor(
    private activatedRoute: ActivatedRoute,
    private tokenStorageService: TokenStorageService
  ) {}
  user:UserResponseInfo = {} as UserResponseInfo;
  ngOnInit(): void {
    const root_url = this.activatedRoute.snapshot.routeConfig?.path;
    if( root_url == 'home' || root_url == 'chatbots' ){
        this.activatedRoute.data.subscribe((response:any) =>{
          if(response.userInfo){
            this.user = response.userInfo;
          }
        },
        (error:any) => {
          console.log(error);
        }
        );
        }
  //         console.log(response);
  //         if(response.userInfo.statusCode === 200){
  //           this.user = response.userInfo.data;
  //           this.tokenStorageService.saveUser(response.userInfo.data);
  //         }
  //       },
  //       (error:any) => {
  //         console.log(error);
  //       }
  //       );
  //  }
  }

}
