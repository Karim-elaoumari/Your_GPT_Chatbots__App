import { Component } from '@angular/core';

@Component({
  selector: 'app-chatbot-log',
  templateUrl: './chatbot-log.component.html',
  styleUrls: ['./chatbot-log.component.css']
})
export class ChatbotLogComponent {
  page: number = 0;
  size: number = 10;

  onPageChange(page: number) {
    this.page = page;
  }

}
