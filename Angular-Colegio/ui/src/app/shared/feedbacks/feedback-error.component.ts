import { Input, Component } from '@angular/core';

@Component({
  selector: 'feedback-error',
  templateUrl: './src/app/shared/feedbacks/feedback-error.component.html'
})
export class FeedbackErrorComponent {

  @Input()
  public alerts: Array<IAlert> = [];

  private backup: Array<IAlert>;

  constructor() {
    this.alerts.push({
      id: 1,
      type: 'danger',
      message: 'This is an error alert',
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