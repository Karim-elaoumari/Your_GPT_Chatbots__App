import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChatbotDashboardComponent } from './chatbot-dashboard.component';

describe('ChatbotDashboardComponent', () => {
  let component: ChatbotDashboardComponent;
  let fixture: ComponentFixture<ChatbotDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChatbotDashboardComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChatbotDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
