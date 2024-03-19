import { Component } from '@angular/core';
import { initCollapses, initDropdowns, initFlowbite, initTabs, initTooltips } from 'flowbite';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'chatybot-frontend';
  constructor() {
    console.log('AppComponent initialized');
   }
   ngOnInit(): void {
    }
}
