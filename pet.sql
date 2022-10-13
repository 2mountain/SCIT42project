DROP TABLE members;
DROP TABLE pet;
DROP TABLE board;
DROP TABLE reply;
DROP TABLE challenge;
DROP TABLE memberCoupon;
DROP TABLE shopCoupon;
DROP TABLE doctor;
DROP TABLE facilities;
DROP TABLE facilitiesEvaluation;
DROP TABLE boardLikedData;
DROP table question;
DROP table answer;
DROP table reportData;

DROP SEQUENCE doctor_seq;
DROP SEQUENCE pet_seq;
DROP SEQUENCE boardNumber_seq;
DROP SEQUENCE reply_seq;
DROP SEQUENCE memberCouponNumber_seq;
DROP SEQUENCE challenge_seq;
DROP SEQUENCE facilitiesNumber_seq;
DROP SEQUENCE facilitiesEvaluationNumber_seq;
DROP SEQUENCE boardLikedNumber_seq;
DROP SEQUENCE questionNumber_seq;
DROP SEQUENCE answerNumber_seq;
DROP SEQUENCE reportNumber_seq;

CREATE SEQUENCE doctor_seq;
CREATE SEQUENCE pet_seq;
CREATE SEQUENCE boardNumber_seq;
CREATE SEQUENCE reply_seq;
CREATE SEQUENCE memberCouponNumber_seq;
CREATE SEQUENCE challenge_seq;
CREATE SEQUENCE facilitiesNumber_seq;
CREATE SEQUENCE facilitiesEvaluationNumber_seq;
CREATE SEQUENCE boardLikedNumber_seq;
CREATE SEQUENCE questionNumber_seq;
CREATE SEQUENCE answerNumber_seq;
CREATE SEQUENCE reportNumber_seq;
CREATE SEQUENCE calendar_seq
       INCREMENT BY 1
       START WITH 1
       MINVALUE 1
       MAXVALUE 9999
       NOCYCLE
       NOCACHE
       NOORDER;

CREATE TABLE members
(
    memberId 					VARCHAR2(40) PRIMARY KEY,
    			--ȸ�� ���̵�
    memberPassword 				VARCHAR2(100) NOT NULL,
    			--ȸ�� ��й�ȣ
    memberNickName 				VARCHAR2(40) NOT NULL UNIQUE,
    			--ȸ�� �г��� 
    memberPostCode 				VARCHAR2(100),
    			--ȸ�� �����ȣ 
    memberAddress 				VARCHAR2(300) NOT NULL,
    			--ȸ�� �ּ�
    memberDetailAddress 		VARCHAR2(100),
    			--ȸ�� ���ּ� 
    memberEmail 				VARCHAR2(50) NOT NULL UNIQUE,
    			--ȸ�� �̸���
    memberBirthDay 				VARCHAR2(12) NOT NULL,
    			--ȸ�� �������
    memberPoint 				NUMBER DEFAULT 0,
    			--ȸ�� ����Ʈ
    memberRoleName 				VARCHAR2(20) DEFAULT 'ROLE_USER'  
			 					CHECK(memberRoleName IN('ROLE_USER', 'ROLE_ADMIN', 'ROLE_DOCTOR')) 
			 					NOT NULL,
    			--ȸ�� ����        
    memberEnabled 				NUMBER DEFAULT 1 CHECK(memberEnabled IN(0, 1)),
    			--ȸ�� ���� ����
    memberJoinDate 				DATE DEFAULT SYSDATE,
    			--ȸ�� ������
    memberImageOriginalFile 	VARCHAR2(300) DEFAULT 'defaultImage',
                --��ǥ �̹����� ���� �̸�
    memberImageSavedFile 		VARCHAR2(100)
                --�̹��� ÷������ ������ ����� �̸�
);



CREATE TABLE pet    --Pet table
(
    petNumber 				NUMBER PRIMARY KEY, 
    			--�ֿϵ��� ��Ϲ�ȣ
    memberId 				VARCHAR2(40) REFERENCES members(memberId),
    			--ȸ�� ���̵� ����
    petName 				VARCHAR2(20) NOT NULL, 
    			--�ֿϵ��� �̸�
    petBreed 				VARCHAR2(40) NOT NULL CHECK(petBreed in('��Ƽ��', 'ġ�Ϳ�', '���޶�Ͼ�', 'Ǫ��', '����', '�ùٰ�', '���', '�����ڱ�', '��Ŀ���дϾ�', '�����ݸ�', '��縮Ʈ����', '�޸��þ�', '��������', '�׷����Ͽ��', '�㽺Ű', '��Ÿ(�����Է�)')), 
    			--�ֿϵ��� ǰ��
    petGender 				VARCHAR2(1) NOT NULL CHECK(petGender in('M', 'F')), 
    			--�ֿϵ��� ����
    petBirthDay 			VARCHAR2(12) NOT NULL, 
    			--�ֿϵ��� �������
    petWeight 				NUMBER default 0 NOT NULL, 
    			--�ֿϵ��� ������
    petNeuter 				VARCHAR2(1) CHECK(petNeuter IN('Y', 'N')), 
    			--�߼�ȭ ����
    petImage 				VARCHAR2(300)
    			--�ֿϵ��� ������ �̸�
);

/*
	petBreed
		������ - ��Ƽ�� ġ�Ϳ� ���޶�Ͼ� Ǫ�� ����
		������ - �ùٰ� ��� �����ڱ� ��Ŀ���дϾ� �����ݸ�
		������ - ��縮Ʈ���� �޸��þ� �������� �׷����Ͽ�� �㽺Ű
		�����Է�
*/

CREATE TABLE BOARD
(
    boardNumber number PRIMARY KEY,
		/* �� ��ȣ(BoardNumber_seq���) */
    memberId varchar2(40) REFERENCES Members(memberId),
		/* ȸ�� ���̵� */
    boardTitle varchar2(300) NOT NULL,
		/* �� ���� */
    memberNickName varchar2(40),
		/* �ۼ��� �г��� */                                                       
    boardInputDate date DEFAULT SYSDATE,
		/* �� �ۼ��� */
    boardHits number DEFAULT 0,
		/* �� ��ȸ�� */
    boardReport number DEFAULT 0,
		/* �� �Ű�� */
    boardContents varchar2(4000) NOT NULL,
		/* �� ���� */
    boardCategory varchar2(20) default '��ü' CHECK (boardCategory IN ('��ü', '�ϻ�', '����', 'Tip')),
		/* �� ���Ӹ� */
    boardImageOriginal varchar2(300),
		/* �Խñ� �̹��� ÷������*/
    boardImageSaved varchar2(100),
		/* ������ ����� ÷������ �̸�*/
	replyCount number
		-- �Խñۿ� �޸� ��ü ��� ����
);



create table boardLikedData
(
    boardLikedNumber number primary key not null,
    memberId varchar2(40) references members(memberId),
    boardNumber number references board(boardNumber)
);



CREATE TABLE reply
(
    replyNumber 	NUMBER PRIMARY KEY,  
    			--��� ��ȣ
    boardNumber NUMBER REFERENCES board(boardNumber) on delete cascade, 
    			--�� ��ȣ(�ܷ�Ű)
	memberId		VARCHAR2(40) NOT NULL,
    			--��� �ۼ��ϴ� ȸ�� ���̵�
    memberNickName 	VARCHAR2(40) NOT NULL,
    			--��� �ۼ��ϴ� ȸ�� �г���
    replyContents 	VARCHAR2(4000) NOT NULL, 
    			--��� ����
    replyInputDate 	DATE DEFAULT SYSDATE
    			--��� �ۼ� ����
);

CREATE TABLE shopCoupon
(
	couponType		VARCHAR2(100) PRIMARY KEY,  -- ������ ����(�̸�)
	couponPrice	 	NUMBER NOT NULL,            -- ��������Ʈ ����
	couponEndDate 	VARCHAR2(12)                -- ���� ������
);

CREATE TABLE memberCoupon
(
    memberCouponNumber 	NUMBER PRIMARY KEY,   
    			--���� �ĺ� ��ȣ
    memberId 		            	VARCHAR2(40) NOT NULL REFERENCES members(memberid),
                --ȸ�� ���̵�
    couponType 			        VARCHAR2(100) NOT NULL REFERENCES shopCoupon(couponType)      
    			--���� ����(�̸�)
);

--�������� �������̺�
CREATE TABLE challenge 
(
   challengeNumber    NUMBER PRIMARY KEY,
   challengeName       VARCHAR2(200),
   challengeStartDate    VARCHAR2(200),
   challengeEndDate    VARCHAR2(200),
   challengeContents    VARCHAR2(200),
   challengeType    VARCHAR2(200),
   challengeRegistDay date,
   challengeSavedFile	VARCHAR2(100),
   challengeOriginalFile	VARCHAR2(300)
);

--�ǻ� ���� ���̺�
CREATE TABLE doctor  -- DOCTOR CHECK ���̺��� �ִٸ� �⺻Ű ���� �ʿ�
(
    doctorNumber 				NUMBER CONSTRAINT doctor_doctorNumber_pk PRIMARY KEY,                              
    			--�ǻ�ȸ����ȣ
    memberId 					VARCHAR2(40) CONSTRAINT doctor_memberId_fk REFERENCES members(memberId),  
    			--ȸ�� ��ȣ
    doctorName 					VARCHAR2(20) CONSTRAINT doctor_doctorName_nn NOT NULL,                               
    			--�ǻ� ȸ�� �̸�
    hospitalName 				VARCHAR2(40), 
    			--�����̸�
    licenseGetDate 				DATE, 
    			--�ڰ��� �����
    licenseImageOriginalFile 	VARCHAR2(300) NOT NULL,
    			--���ǻ� �ڰ��� �̹��� ÷�������� ���� �̸�
    licenseImageSavedFile 		VARCHAR2(100),  
    			--�ڰ��� �̹��� ÷������ ������ ����� �̸�
    doctorApproval 				VARCHAR2(10) CONSTRAINT doctor_doctorApproval_ck CHECK(doctorApproval IN('Y','Wait', 'N')),
    			--�ǻ� ���� ���� ����
	doctorApplicationDate 		DATE DEFAULT SYSDATE, 
				--�ǻ� ���� ��û��
    doctorAcceptedDate			DATE 
    			--�ǻ� ���� ������
);

CREATE TABLE facilities 
(
	facilitiesNumber   NUMBER CONSTRAINT facilities_facilitiesNumber_pk PRIMARY KEY,
                -- �ü� ��ȣ
	facilitiesName      VARCHAR2(100) CONSTRAINT facilities_faciliteisName_NN NOT NULL,
                -- �ü� �̸�
    facilitiesAddress VARCHAR2(300),
                -- �ü� ���θ� �ּ�
    facilitiesDetailAddress VARCHAR2(300),
                -- �ü� ���� �ּ�
    facilitiesPhoneNumber VARCHAR2(40)
                -- �ü� ��ȭ��ȣ
);

CREATE TABLE facilitiesEvaluation
(
	facilitiesEvaluationNumber  	NUMBER PRIMARY KEY,
                -- �ü� �� ��ȣ
	facilitiesNumber                NUMBER REFERENCES facilities(facilitiesNumber),
                -- �ü� ��ȣ
	memberId                        VARCHAR2(40) REFERENCES members(memberId),
                -- ���� �ۼ� ȸ�� ���̵�
	facilitiesReview                VARCHAR2(1000) NOT NULL,
                -- �ü� ���� ����
	facilitiesStar                  NUMBER CHECK(facilitiesStar IN(0, 1, 2, 3, 4, 5)) NOT NULL,
                -- �ü� ��(����)
	facilitiesReviewDate            DATE DEFAULT SYSDATE
                -- �ü� ���� �ۼ� ����
);
create table entrylist
(
     entrylistNumber number primary key,
	 memberId varchar2(40)  references  MEMBERS(MEMBERID),
	 challengeNumber number references CHALLENGE(CHALLENGENUMBER),
	 challengeSuccess  number,
	 getPoint number,
     getPointDate DATE default sysdate
);

CREATE TABLE question
(
    questionNumber number PRIMARY KEY,
		/* ������ ��ȣ(BoardNumber_seq���) */
    memberId varchar2(40) REFERENCES Members(memberId),
		/* ȸ�� ���̵� */
    questionTitle varchar2(300) NOT NULL,
		/* ������ ���� */
    memberNickName varchar2(40),
		/* �ۼ��� �г��� */
    questionInputDate date DEFAULT SYSDATE,
		/* ������ �ۼ��� */
    questionHits number DEFAULT 0,
		/* ������ ��ȸ�� */
    questionReport number DEFAULT 0,
		/* ������ �Ű�� */
    questionContents varchar2(4000) NOT NULL,
		/* ������ ���� */
    questionCategory varchar2(20) default '��ü' CHECK (questionCategory IN ('�Ϲ�', '�Ƿ�')),
		/* ������ ���Ӹ� */
    questionImageOriginal varchar2(300),
		/* ������ �̹��� ÷������*/
    questionImageSaved varchar2(100),
		/* ������ ����� ÷������ �̸�*/
    answerCount number default 0,
		-- �����ۿ� �޸� ��ü �亯 ����
    answeredOrNot number default 0 check (answeredOrNot IN (0, 1)),
		-- �亯�� �޷ȴ��� �� �޷ȴ��� ����
    answerAccepted	number default 0 check(answerAccepted in (0,1 ))
        --�亯 ä�� ����
);

CREATE TABLE answer
(
    answerNumber    NUMBER PRIMARY KEY,  
    			--��� ��ȣ
    questionNumber  NUMBER REFERENCES question(questionNumber) on delete cascade, 
    			--�� ��ȣ(�ܷ�Ű)
    memberId        VARCHAR2(40) NOT NULL,
    			--�亯 �ۼ��ϴ� ȸ�� ���̵�
    memberNickName  VARCHAR2(40) NOT NULL,
    			--�亯 �ۼ��ϴ� ȸ�� �г���
    memberRoleName  VARCHAR2(20) default 'ROLE_USER' CHECK (memberRoleName IN ('ROLE_ADMIN', 'ROLE_DOCTOR', 'ROLE_USER')),
                --�亯 �ۼ��ϴ� ȸ�� ���
    answerContents 	VARCHAR2(4000) NOT NULL,
    			--�亯 ����
    answerInputDate DATE DEFAULT SYSDATE
    			--�亯 �ۼ� ����
);

create table reportData
(
    reportNumber NUMBER primary key not null,
        --�Ű� �ĺ���ȣ
    reportCategory VARCHAR2(200) not null,
        --�Ű� ���� ���� ����(ȫ��/����, ������, �ҹ�����, �弳/����ǥ��, �������� ����)
    memberId VARCHAR2(40) references members(memberId) on delete cascade,
        --�Ű��� ��� ���̵�
    boardNumber NUMBER references board(boardNumber) on delete cascade,
        --�Ű���� Ŀ�´�Ƽ �Խñ۹�ȣ
	questionNumber NUMBER references question(questionNumber) on delete cascade
        --�Ű���� ������ �Խñ۹�ȣ
);

CREATE TABLE "CALENDAR"
(
	"SEQ" NUMBER(*,0) NOT NULL ENABLE, 
	"TITLE" VARCHAR2(100 BYTE) NOT NULL ENABLE, 
	"START_DATE" DATE NOT NULL ENABLE, 
	"END_DATE" DATE NOT NULL ENABLE
);






	insert into BOARD (
		boardNumber
		, memberId
		, boardTitle
		, memberNickName
		, boardContents
		, boardCategory
		)
	values (
		boardNumber_seq.nextval
		, 'khs11111' --��� ���̵�
		, 'testTitle' --������
		, 'khs11111' --����г���
		, 'testContents' --�۳���
		, '�ϻ�' --�� ī�װ�(�ϻ�, ����, Tip)
		);
        
        insert into question (
		questionNumber
		, memberId
		, questionTitle
		, memberNickName
		, questionContents
		, questionCategory
		)
		values (
		questionNumber_seq.nextval
		, 'khs11111' --������̵�
		, 'testTitle'
		, 'khs11111' --����г���
		, 'testContents'
		, '�Ϲ�' --������ ī�װ�(�Ϲ�, �Ƿ�)
		);

select * from members;

commit;
