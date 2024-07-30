FROM golang:1.21-alpine3.20 AS build
LABEL MAINTAINER = 'Auth AngelJoel'
WORKDIR /app
COPY go.mod go.sum ./
RUN go mod download
COPY . .

RUN CGO_ENABLED=0 GOOS=linux GOARCH=amd64 go build -o /product-service -ldflags="-w -s" ./cmd/main.go

FROM scratch
WORKDIR /
COPY --from=build product-service/ /product-service

CMD ["/product-service"]
