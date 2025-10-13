CREATE EXTENSION IF NOT EXISTS postgis;

ALTER TABLE catastros.predio
  ALTER COLUMN geometria
  TYPE geometry(Polygon,4326)
  USING CASE
          WHEN ST_SRID(geometria) IS DISTINCT FROM 4326
            THEN ST_SetSRID(geometria, 4326)
          ELSE geometria
        END::geometry(Polygon,4326);

DO $$
BEGIN
  IF NOT EXISTS (
    SELECT 1
    FROM   pg_indexes
    WHERE  schemaname = 'catastros'
       AND tablename  = 'predio'
       AND indexname  = 'predio_geometria_gix'
  ) THEN
    EXECUTE 'CREATE INDEX predio_geometria_gix ON catastros.predio USING GIST (geometria)';
  END IF;
END $$;
