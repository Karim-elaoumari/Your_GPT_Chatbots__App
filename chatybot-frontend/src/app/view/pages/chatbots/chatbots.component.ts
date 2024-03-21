import { Component } from '@angular/core';
import { UserResponse } from 'src/app/core/models/UserResponse';
import { TokenStorageService } from 'src/app/core/services/TokenStorageService';

@Component({
  selector: 'app-chatbots',
  templateUrl: './chatbots.component.html',
  styleUrls: ['./chatbots.component.css']
})
export class ChatbotsComponent {
  constructor(private tokenStorageService:TokenStorageService) {}
  user:UserResponse = {} as UserResponse;
  ngOnInit() {
    this.user = this.tokenStorageService.getUser();

  }
  signOut(): void {
    this.tokenStorageService.signOut();
    window.location.reload();
  }

}
