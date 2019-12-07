CREATE CACHED TABLE PUBLIC.USER(
    ID INT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_0093FDDC_BBCF_4E95_AAA8_B9FABDFD5926) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_0093FDDC_BBCF_4E95_AAA8_B9FABDFD5926,
    ACCOUNT_ID VARCHAR(100),
    NAME VARCHAR(50),
    TOKEN CHAR(36),
    GMT_CREATE BIGINT,
    GMT_MODIFIED BIGINT
)
