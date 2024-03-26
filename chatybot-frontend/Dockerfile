FROM node:18.18.2 as build
WORKDIR /app
COPY package.json package-lock.json ./
RUN npm install
COPY . .
RUN npm run build

FROM nginx:stable
# Copy the built app
COPY --from=build /app/dist/chatybot-frontend/ /usr/share/nginx/html
# Overwrite the default Nginx config with your custom one
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
