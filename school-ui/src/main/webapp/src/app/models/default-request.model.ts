/**
 * The default request we are going  to make to the rest API.
{
  "txId": "e09aae68-4e8e-4c84-b0a9-e53876ab5772", // Transaction ID
  "sesionId": "9cd84607-fbb7-45a3-8521-f6dbcf14e52a", // Session ID
  "moduleId": "module", // Module name
  "sei": "/login", // Endpoint name
  "body": { } // Any aditional data
}
 */
export class DefaultRequest {

  public txId: string;
  public sessionId: string;
  public moduleId: string;
  public sei: string;
  public body?: any;

  constructor(txId: string, sessionId: string, moduleId: string, sei: string, body?: any) {
    this.txId = txId;
    this.sessionId = sessionId;
    this.moduleId = moduleId;
    this.sei = sei;
    this.body = body;
  }

  setBody(body: any) {
    this.body = body;
  }
}