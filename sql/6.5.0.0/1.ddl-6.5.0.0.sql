ALTER SESSION SET CURRENT_SCHEMA= {{schema}} ;
CREATE TABLE CREW_MODIFICATIONS 
   (	
    "ID" NUMBER(*,0),
    "CREW_ID" NUMBER(38),
    "DEALER_NAME" VARCHAR2(500),
    "DEALER" VARCHAR2(500),
    "MEMBERS_CREW" VARCHAR2(4000),
    "DOCUMENT_NUMBER" VARCHAR2(4000),
    "RESPONSIBLE_CREW" VARCHAR2(200),
    "ROL_MEMBERS_CREW" VARCHAR2(4000),
    "CREATION_DATE" TIMESTAMP(6),
    "ACTIVATION_CREW_DATE" TIMESTAMP(6),
    "MODIFICATION_CREW_DATE" TIMESTAMP(6),
    "NEW_MEMBER" VARCHAR2(200),
    "DOC_NUMBER_NEW_MEMBER" VARCHAR2(200),
    "NEW_RESPONSIBLE_CREW" VARCHAR2(200),
    "ROL_NEW_MEMBER" VARCHAR2(200),
    "LOCATION_CODE" VARCHAR2(200),
    "CREW_STATUS" VARCHAR2(200),
    "VEHICLE_ID"  NUMBER(38) NOT NULL,
    "USER_ID" NUMBER(38) NOT NULL,
    
    CONSTRAINT MODIFICATION_CREW_DATE_PK PRIMARY KEY (ID) ENABLE
   ) ;

   
CREATE SEQUENCE  "SEQ_CREW_MODIFICATIONS"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;

CREATE INDEX CREW_MODIFICATIONS_IDX01 ON CREW_MODIFICATIONS ( MODIFICATION_CREW_DATE DESC);

ALTER TABLE "CREW_MODIFICATIONS" ADD CONSTRAINT "CREW_MOVEMENTS_VEHICLE_FK" FOREIGN KEY ("VEHICLE_ID")
	  REFERENCES "VEHICLES" ("ID") ENABLE;	  
	  
ALTER TABLE "CREW_MODIFICATIONS" ADD CONSTRAINT "CREW_MOVEMENTS_USERS_FK" FOREIGN KEY ("USER_ID")
	  REFERENCES "USERS" ("ID") ENABLE;
	  
CREATE TABLE WORK_ORDER_CREW_ATTENTIONS 
   (	
    "ID" NUMBER(*,0), 
    "DEALER_NAME" VARCHAR2(500),
    "DEALER" VARCHAR2(500),
    "WO_CODE" VARCHAR2(50),
    "CREW_ID" NUMBER(38),
    "MEMBERS_CREW" VARCHAR2(4000),
    "NAME_TECHNICIAL_ATTENTION" VARCHAR2(100),
    "DOCUMENT_NUMBER" VARCHAR2(200),
    "IBS_TECHNICIAL" VARCHAR2(200),
    "ATTENTION_DATE" TIMESTAMP(6),
    CONSTRAINT WORK_ORDER_CREW_ATTENTIONS_PK PRIMARY KEY (ID) ENABLE
   ) ;
   
CREATE SEQUENCE  "SEQ_WORK_ORDER_CREW_ATTENTIONS"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;

CREATE INDEX WO_CREW_ATTENTIONS_IDX01 ON WORK_ORDER_CREW_ATTENTIONS ( ATTENTION_DATE DESC);

CREATE INDEX ADJUSTMENT_ELEMENTS_IDX01 ON ADJUSTMENT_ELEMENTS (ADJUSTMENT_ID);

CREATE INDEX WAREHOUSE_ELEMENTS_IDX13 ON WAREHOUSE_ELEMENTS (WAREHOUSE_ID,RECORD_STATUS_ID);

commit;