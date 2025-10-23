ALTER TABLE token_bling
ALTER COLUMN expires_in TYPE TIMESTAMP
USING to_timestamp(expires_in);
