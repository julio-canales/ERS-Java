/*******************************************************************************
   Create Tables
********************************************************************************/
CREATE TABLE "ers_reimbursement" (
	"reimb_id" SERIAL NOT NULL,
	"reimb_amount" NUMERIC(8, 2) NOT NULL,
	"reimb_submitted" TIMESTAMP NOT NULL,
	"reimb_resolved" TIMESTAMP,
	"reimb_description" VARCHAR(250) NOT NULL,
	"reimb_receipt" bytea,
	"reimb_author" INT NOT NULL,
	"reimb_resolver" INT,
	"reimb_status_id" INT NOT NULL,
	"reimb_type_id" INT NOT NULL,
	CONSTRAINT "ers_reimbursement_pk" PRIMARY KEY ("reimb_id")
);

CREATE TABLE "ers_users" (
	"ers_user_id" SERIAL NOT NULL,
	"ers_username" VARCHAR(50) NOT NULL,
	"ers_password" VARCHAR(50) NOT NULL,
	"user_first_name" VARCHAR(100) NOT NULL,
	"user_last_name" VARCHAR(100) NOT NULL,
	"user_email" VARCHAR(150) NOT NULL,
	"user_role_id" INT NOT NULL,
	CONSTRAINT "ers_users_pk" PRIMARY KEY ("ers_user_id")
);

CREATE TABLE "ers_reimbursement_status" (
	"reimb_status_id" SERIAL NOT NULL,
	"reimb_status" VARCHAR(20) NOT NULL,
	CONSTRAINT "reimb_status_pk" PRIMARY KEY ("reimb_status_id")
);

CREATE TABLE "ers_reimbursement_type" (
	"reimb_type_id" SERIAL NOT NULL,
	"reimb_type" VARCHAR(10) NOT NULL,
	CONSTRAINT "reimb_type_pk" PRIMARY KEY ("reimb_type_id")
);

CREATE TABLE "ers_user_roles" (
	"ers_user_role_id" SERIAL NOT NULL,
	"user_role" VARCHAR(10) NOT NULL,
	CONSTRAINT "ers_user_roles_pk" PRIMARY KEY ("ers_user_role_id")
);

/*******************************************************************************
   Create Foreign Keys
********************************************************************************/
ALTER TABLE "ers_reimbursement" ADD CONSTRAINT "ers_users_fk_auth"
	FOREIGN KEY ("reimb_author") REFERENCES "ers_users" ("ers_user_id");
	
ALTER TABLE "ers_reimbursement" ADD CONSTRAINT "ers_users_fk_resolver"
	FOREIGN KEY ("reimb_resolver") REFERENCES "ers_users" ("ers_user_id");
	
ALTER TABLE "ers_reimbursement" ADD CONSTRAINT "ers_reimbursement_status_fk"
	FOREIGN KEY ("reimb_status_id") REFERENCES "ers_reimbursement_status" ("reimb_status_id");
	
ALTER TABLE "ers_reimbursement" ADD CONSTRAINT "ers_reimbursement_type_fk"
	FOREIGN KEY ("reimb_type_id") REFERENCES "ers_reimbursement_type" ("reimb_type_id");

ALTER TABLE "ers_users" ADD CONSTRAINT "user_roles_fk"
	FOREIGN KEY ("user_role_id") REFERENCES "ers_user_roles" ("ers_user_role_id"); 
	
/*******************************************************************************
   Populate Tables
********************************************************************************/
INSERT INTO "ers_reimbursement_status" ("reimb_status_id", "reimb_status") VALUES (1, 'Pending');
INSERT INTO "ers_reimbursement_status" ("reimb_status_id", "reimb_status") VALUES (2, 'Approved');
INSERT INTO "ers_reimbursement_status" ("reimb_status_id", "reimb_status") VALUES (3, 'Denied');

INSERT INTO "ers_user_roles" ("ers_user_role_id", "user_role") VALUES (1, 'Employee');
INSERT INTO "ers_user_roles" ("ers_user_role_id", "user_role") VALUES (2, 'Manager');

INSERT INTO "ers_reimbursement_type" ("reimb_type_id", "reimb_type") VALUES (1, 'Travel');
INSERT INTO "ers_reimbursement_type" ("reimb_type_id", "reimb_type") VALUES (2, 'Lodging');
INSERT INTO "ers_reimbursement_type" ("reimb_type_id", "reimb_type") VALUES (3, 'Food');
INSERT INTO "ers_reimbursement_type" ("reimb_type_id", "reimb_type") VALUES (4, 'Other');