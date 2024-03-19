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
    this.user = this.tokenStorageService.getUser();
  }

}
