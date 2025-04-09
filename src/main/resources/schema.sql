BEGIN TRY
    -- Add active column if it doesn't exist
    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('users') AND name = 'active')
    BEGIN
        ALTER TABLE users ADD active bit NOT NULL CONSTRAINT DF_users_active DEFAULT 1;
    END
END TRY
BEGIN CATCH
    -- If error occurs, try alternative approach
    IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE object_id = OBJECT_ID('users') AND name = 'active')
    BEGIN
        ALTER TABLE users ADD active bit NULL;
        UPDATE users SET active = 1;
        ALTER TABLE users ALTER COLUMN active bit NOT NULL;
    END
END CATCH