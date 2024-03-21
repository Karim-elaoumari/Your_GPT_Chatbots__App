import { Component, ElementRef, ViewChild } from '@angular/core';
import { Data } from '@angular/router';
import { Store } from '@ngrx/store';
import { BotData } from 'src/app/core/models/BotData';
import { BotTextdata } from 'src/app/core/models/BotTextData';
import { ChatBot } from 'src/app/core/models/ChatBot';
import { UserResponse } from 'src/app/core/models/UserResponse';
import { TokenStorageService } from 'src/app/core/services/TokenStorageService';
import { getChatBotSelector } from 'src/app/state/chatbot/selector';
import { LoadingStatus } from 'src/app/state/chatbot/state';
import { CreateData, CreateFileData, DeleteData, LoadData } from 'src/app/state/data/action';
import { getDataDeletingStatusSelector, getDataErrorSelector, getDataLoadingStatusSelector, getDataSavingStatusSelector, getDataSelector } from 'src/app/state/data/selector';

@Component({
  selector: 'app-chatbot-data',
  templateUrl: './chatbot-data.component.html',
  styleUrls: ['./chatbot-data.component.css']
})
export class ChatbotDataComponent {
 
  constructor(
    private tokenStorageService:TokenStorageService,
    private store: Store<any>,
  ) {
    
   }
   @ViewChild('dropzoneFile') dropzoneFile!: ElementRef;
   border_color: string = '';
   page: number = 0;
   size: number = 10;
   user:UserResponse = {} as UserResponse;
   new_text_data:string = '';
   fileName: string = ''; 
   selected_file:File = {} as File;
   error: string = '';
   chat_bot:ChatBot = {} as ChatBot;
   success: string = '';
   bot_data: BotData[] = [];

   loading: boolean = false;
   data_loading: string = 'Waiting for Data to load...';
  ngOnInit(): void {
    this.user = this.tokenStorageService.getUser();
    this.store.select(getChatBotSelector).subscribe(
      (chat_bott:ChatBot) => {
        this.chat_bot = chat_bott;
        if(this.chat_bot.id!=undefined){
          this.getAllData();
        }
      }
    );
  }
  onPageChange(page: number){
    this.page = page;
    this.getAllData();
  }

  onDragOver(event: DragEvent): void {
    event.preventDefault();
    this.border_color = '#007bff';
  }

  onDragLeave(event: DragEvent): void {
    event.preventDefault();
    this.border_color = '';
  }

  onDrop(event: DragEvent): void {
    event.preventDefault();
    if (event.dataTransfer && event.dataTransfer.files) {
      const file = event.dataTransfer.files[0];
      this.fileName = file.name;
      this.selected_file = file;
      this.border_color = '#6f42c1';
    }
    
  }
  
  onFileSelected(event: any): void {
    this.selected_file = event.target.files[0];
    if (this.selected_file){
      this.fileName = this.selected_file.name; 

    }
  }
  addTextData(){
    if(this.new_text_data != '' && this.new_text_data.length > 200){
      this.error = '';
      this.success = 'Adding text data...';
      this.loading = true;
      let text_data:BotTextdata = {
        chatBotId: this.chat_bot.id,
        text: this.new_text_data
      } as BotTextdata;
      this.store.dispatch(new CreateData(text_data));
      this.store.select(getDataSavingStatusSelector).subscribe(
        (loading:LoadingStatus) => {
          if(loading == LoadingStatus.LOADED){
            this.success = 'Text data added successfully';
            this.error = '';
            this.new_text_data = '';
            this.loading = false;
          }else if(loading == LoadingStatus.ERROR){
            this.error = 'Error adding text data';
            this.success = '';
            this.loading = false;
          }
        }
      );
      
    }else{
      this.error = 'The text must be at least 200 characters long';
    }
  }
  addFileData(){
    if(this.selected_file){
      this.error = '';
      this.success = 'Adding file data...';
      this.loading = true;

      this.store.dispatch(new CreateFileData({chat_bot_id:this.chat_bot.id, file:this.selected_file}));
      this.store.select(getDataSavingStatusSelector).subscribe(
        (loading:LoadingStatus) => {
          if(loading == LoadingStatus.LOADED){
            this.success = 'File data added successfully';
            this.error = '';
            this.loading = false;
            this.dropzoneFile.nativeElement.value = '';
            this.fileName = '';
          }else if(loading == LoadingStatus.ERROR){
            this.error = 'Error adding file data';
            this.success = '';
            this.loading = false;
          }
        }
      );
  }
  }
  getAllData(){
    this.store.dispatch(new LoadData({chat_bot_id:this.chat_bot.id,page:this.page, size:this.size}));
    this.selectData();
   
  }
  selectData(){
    this.data_loading = 'Waiting for Data to load...';
    this.store.select(getDataSelector).subscribe(
      (data:  BotData[]) => {
        this.bot_data = data;
        this.store.select(getDataLoadingStatusSelector).subscribe(
          (loading:LoadingStatus) => {
            if(loading == LoadingStatus.LOADED){
              this.data_loading = '';
            }else if(loading == LoadingStatus.ERROR){
              this.data_loading = 'Error Loading Data';
            }
          }
        );
      }
    )
  }
  deleteData(data_id:string){
    this.store.dispatch(new DeleteData(data_id));
    this.loading = true;
    this.store.select(getDataDeletingStatusSelector).subscribe(
      (loading:LoadingStatus) => {
        if(loading == LoadingStatus.LOADED){
          this.success = 'Data deleted successfully';
          this.error = '';
          this.loading = false;
        }else if(loading == LoadingStatus.ERROR){
          this.store.select(getDataErrorSelector).subscribe(
            (error:string) => {
              this.error = error;
              this.success = '';
              this.loading = false;
            }
          );
         
        }
      }
    );
  }

}
