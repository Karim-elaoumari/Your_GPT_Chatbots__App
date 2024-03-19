
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { AllowedOrigins } from 'src/app/core/models/AllowedOrigins';
import { ChatBot } from 'src/app/core/models/ChatBot';
import { ChatBotUpdateValidation } from 'src/app/core/validators/ChatBotUpdateValidation';
import { CreateChatBotOrigin, DeleteChatBot, DeleteChatBotOrigin, UpdateChatBot } from 'src/app/state/chatbot/action';
import { getChatBotErrorSelector, getChatBotLoadingStatusSelector, getChatBotSelector, getOriginsLoadingStatusSelector } from 'src/app/state/chatbot/selector';
import { LoadingStatus } from 'src/app/state/chatbot/state';

@Component({
  selector: 'app-chatbot-settings',
  templateUrl: './chatbot-settings.component.html',
  styleUrls: ['./chatbot-settings.component.css']
})
export class ChatbotSettingsComponent {
  constructor(
    private store: Store<any>,
    private chatbotUpdateValidation:ChatBotUpdateValidation,
    private router:Router
  ) {}
  chat_bot:ChatBot = {} as ChatBot;
  initial_chat_bot:ChatBot = {} as ChatBot;
  chat_bot_store:Observable<any> = this.store.select(getChatBotSelector);
  chat_bot_validation_errors:{ [key: string]: string } = {};
  error: string = '';
  success: string = '';
  new_origin:string = '';
  delete_chat_bot_name:string = '';

  ngOnInit() {
    this.getChatBotDetails();
  }
  getChatBotDetails(){
    this.chat_bot_store.subscribe(
      (chat_bott:ChatBot) => {
        this.chat_bot = {...chat_bott};
        this.initial_chat_bot = {...chat_bott};
      },
      (error:any) => {
        console.log(error);
      }
    );
  }
  updateChatBot(){
    if(this.validateChatBotInputs()){
      this.success = 'Updating Chat Bot...';
      this.store.dispatch(new UpdateChatBot({...this.chat_bot}));
      this.store.select(getChatBotLoadingStatusSelector).subscribe(
        (loading:LoadingStatus) => {
          if(loading == LoadingStatus.LOADED){
            this.store.select(getChatBotErrorSelector).subscribe(
              (error:any) => {
                if(error == ''){
                  this.success = 'Chat Bot Updated Successfully';
                  this.error = '';
                  this.initial_chat_bot = {...this.chat_bot};
                }else{
                  this.error = error;
                  this.success = '';
                }
              }
            );
          }
          else if(loading == LoadingStatus.ERROR){
            this.error = 'An error occurred while updating chat bot';
            this.success = '';
          }
        } 
      );
    }
    
  }
  validateChatBotInputs(){
    this.chat_bot_validation_errors = this.chatbotUpdateValidation.validate(this.chat_bot);
    if(Object.keys(this.chat_bot_validation_errors).length === 0){
      return true;
    }
    return false;
  }
  refreshBotUi(){
    this.chat_bot = {...this.initial_chat_bot};
  }
  deleteOrigin(origin:AllowedOrigins){
     this.store.dispatch(new DeleteChatBotOrigin({chat_bot_id:this.chat_bot.id, origin:origin.origin}));
     this.success = 'Deleting Origin...';
      this.store.select(getOriginsLoadingStatusSelector).subscribe(
        (loading:LoadingStatus) => {
          if(loading == LoadingStatus.LOADED){
            this.store.select(getChatBotErrorSelector).subscribe(
              (error:any) => {
                if(error == ''){
                  this.success = 'Origin Deleted Successfully';
                  this.error = '';
                }else{
                  this.error = error;
                  this.success = '';
                }
              }
            );
          }
          else if(loading == LoadingStatus.ERROR){
            this.error = 'An error occurred while deleting origin';
            this.success = '';
          } 
        }
      );

  }
  createOrigin(){
    if(this.new_origin != '' && this.new_origin.length > 3){
      let domain = this.domainFromOrigin(this.new_origin);
      this.store.dispatch(new CreateChatBotOrigin({chat_bot_id:this.chat_bot.id, origin:domain}));
      this.success = 'Adding Origin...';
      this.store.select(getOriginsLoadingStatusSelector).subscribe(
        (loading:LoadingStatus) => {
          if(loading == LoadingStatus.LOADED){
            this.store.select(getChatBotErrorSelector).subscribe(
              (error:any) => {
                if(error == ''){
                  this.success = 'Origin Added Successfully';
                  this.error = '';
                  this.new_origin = '';
                }else{
                  this.error = error;
                  this.success = '';
                }
              }
            );
          }
          else if(loading == LoadingStatus.ERROR){
            this.error = 'An error occurred while adding origin';
            this.success = '';
          } 
        }
      );
    }else{
      this.error = 'Invalid Origin';
    }
  }
  domainFromOrigin(origin:string){
    if(origin.includes('https://')){
      return origin.replace('https://','');
    }else if( origin.includes('http://')){
      return origin.replace('http://','');
    }
    return origin;
  }
  deleteChatBot(){
    if(this.delete_chat_bot_name==this.chat_bot.name){
      this.store.dispatch(new DeleteChatBot(this.chat_bot.id));
      this.store.select(getChatBotLoadingStatusSelector).subscribe(
        (loading:LoadingStatus)=>{
          if(loading==LoadingStatus.LOADED){
            this.router.navigate(['/chatbots/chatbots-list']);
          }else if(loading==LoadingStatus.ERROR){
            this.error = 'Error while deleting this chatbot';
          }
        }
      )

    }else{
      this.error = 'Chat Bot Name Typed not Correct'
    }

  }

 



}
