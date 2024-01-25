@echo off

rem Docker image name and version
set IMAGE_NAME=movie
set IMAGE_VERSION=dev

rem Check if an image with the same name and version already exists
for /f "tokens=* delims=" %%i in ('docker images -q %IMAGE_NAME%:%IMAGE_VERSION%') do set EXISTING_IMAGE=%%i

rem If an existing image is found, remove it
if not "%EXISTING_IMAGE%"=="" (
    echo Removing existing image: %EXISTING_IMAGE%
    docker rmi %EXISTING_IMAGE%
)

rem Build the Docker image
docker build -t %IMAGE_NAME%:%IMAGE_VERSION% .

rem Tag and push the new image
docker tag %IMAGE_NAME%:%IMAGE_VERSION% %IMAGE_NAME%:%IMAGE_VERSION%
rem docker push %IMAGE_NAME%:%IMAGE_VERSION%

echo Docker image build and replacement completed.
