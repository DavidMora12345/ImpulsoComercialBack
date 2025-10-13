CREATE EXTENSION IF NOT EXISTS postgis;

ALTER TABLE catastros.predio
  ALTER COLUMN geometria
  TYPE geometry(Polygon,4326)
  USING ST_SetSRID(geometria::geometry, 4326)::geometry(Polygon,4326);
