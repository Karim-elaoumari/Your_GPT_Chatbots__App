import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.css']
})
export class PaginationComponent {
  @Input() page: number= 0;
  @Input() size: number= 10;
  @Input() this_page_count: number= 0;
  @Output() pageChange: EventEmitter<number> = new EventEmitter<number>();


  onNextClick() {
    this.pageChange.emit(this.page + 1);
  }

  onPreviousClick() {
    this.pageChange.emit(this.page - 1);
  }
}
