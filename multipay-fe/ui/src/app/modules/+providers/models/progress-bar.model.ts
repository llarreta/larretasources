export class ProgressBar {

    constructor(object: any) {
        this.percentage = object.percentage;
        this.listName = object.listName;
        this.totalItems = object.totalItems;
        this.amount = object.amount;
    }
    /**
     * Maximal value to be displayed in the progressbar.
     */
    public max: number;
    /**
     * A flag indicating if the stripes of the progress bar should be animated. Takes effect only for browsers
     * supporting CSS3 animations, and if striped is true.
     */
    public animated: boolean;
    /**
     * A flag indicating if a progress bar should be displayed as striped.
     */
    public striped: boolean;
    /**
     * Type of progress bar, can be one of "success", "info", "warning" or "danger".
     */
    public type: string;
    /**
     * Current value to be displayed in the progressbar. Should be smaller or equal to "max" value.
     */
    public percentage: number;

    /**
     * The name to be displayed in the side of the progress bar (Optional)
     */
    public listName?: string;

    /**
     * The amount to be displayed in the side of the progress bar (Optional)
     */
    public amount?: string;

    /**
     * The number of items to be displayed in the side of the progress bar (Optional)
     */
    public totalItems?: number;

}
