FROM python
LABEL authors="IsJ01"

WORKDIR /code

COPY . /code/

EXPOSE 8081

RUN pip install -r requirements.txt
RUN uvicorn app.main:app --host localhost --port 8080 --reload