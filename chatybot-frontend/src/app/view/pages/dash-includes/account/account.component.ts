import { Component } from '@angular/core';
import { Store } from '@ngrx/store';
import { Conversation } from 'src/app/core/models/Conversation';
import { UserResponseInfo } from 'src/app/core/models/UserResponseInfo';
import { TokenStorageService } from 'src/app/core/services/TokenStorageService';
import { getConversationsSelector } from 'src/app/state/data/selector';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent {
  constructor(private tokenStorageService:TokenStorageService,
    private store: Store<any>,
  ) {

  }
  user:UserResponseInfo = {} as UserResponseInfo;
  count_conversations:number = 0;
  ngOnInit(): void {
    this.user = this.tokenStorageService.getUser();
  }

}
