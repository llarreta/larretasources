import { AdvancedSearchFilter } from './advanced-search.model';

export class SearchRequest {

  public quantity: number;
  public numPage: number;
  public orderField: string;
  public order: string;
  public filter?: AdvancedSearchFilter;

  constructor() {
    this.quantity = 10;
    this.numPage = 1;
    this.orderField = "date_due";
    this.order = "asc";
  }

  setFilter(filter: any) {
    this.filter = filter;
  }
}