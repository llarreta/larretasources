import { Input, Component } from '@angular/core';

@Component({
  selector: 'feedback-success',
  templateUrl: './src/app/shared/feedbacks/feedback-success.component.html'
})
export class FeedbackSuccessComponent {

  @Input()
  public alerts: Array<IAlert> = [];

  private backup: Array<IAlert>;

  constructor() {
    this.alerts.push({
      id: 1,
      type: 'success',
      message: 'This is an success alert',
    });
    this.backup = this.alerts.map((alert: IAlert) => Object.assign({}, alert));
  }

  public closeAlert(alert: IAlert) {
    const index: number = this.alerts.indexOf(alert);
    this.alerts.splice(index, 1);
  }

  public reset() {
    this.alerts = this.backup.map((alert: IAlert) => Object.assign({}, alert));
  }

}

export interface IAlert {
  id: number;
  type: string;
  message: string;
}