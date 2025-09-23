-- Create Schema

DO
$$
BEGIN
   IF NOT EXISTS (
       SELECT 1 FROM information_schema.schemata WHERE schema_name = 'polls'
   ) THEN
       EXECUTE 'CREATE SCHEMA polls AUTHORIZATION ' || current_user;
END IF;
END
$$;
