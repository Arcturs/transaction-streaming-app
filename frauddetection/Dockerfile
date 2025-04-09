FROM python:3.9-slim
WORKDIR /frauddetection
COPY requirements.txt /frauddetection
RUN pip3 install --upgrade pip
RUN pip3 install -r requirements.txt
COPY . /frauddetection
EXPOSE 5001