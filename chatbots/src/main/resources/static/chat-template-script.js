function toggleIframe() {
    if (iframeVisible) {
        iframe.style.display = 'none';
        toggleButton.style.backgroundImage = 'url("http://localhost:8081/chat-bot.png")';
    } else {
        iframe.style.display = 'block';
        toggleButton.style.backgroundImage = 'url("http://localhost:8081/show-hide-icon.png")'; // Change to your alternate background image
    }

    iframeVisible = !iframeVisible;
}

// Create styles for the button and iframe
var styles = document.createElement('style');
styles.innerHTML = `
                                                    #toggleButton {
                                                        position: fixed;
                                                        bottom: 50px;
                                                        right: 30px;
                                                        z-index: 9999;
                                                        border: none;
                                                        border-radius: 50%;
                                                        width: 50px;
                                                        height: 50px;
                                                        background-image: url('http://localhost:8081/chat-bot.png');
                                                        background-color: white;
                                                        background-size: cover;
                                                        cursor: pointer;
                                                        box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2); /* Add shadow to make it appear floating */
                                                    }
                                            
                                                    #myIframe {
                                                        position: fixed;
                                                        bottom: 80px;
                                                        right: 10px;
                                                        z-index: 9998; /* One less than the button's z-index */
                                                        border: 2px;
                                                        border-radius: 10px 10px 10px 10px !important;
                                                        display: none;
                                                        width: 30%; 
                                                        padding-top:10px;
                                                        box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2); /* Add shadow to make it appear floating */
                                                        height: 83%;
                                                        overflow: hidden;
                                                        
                                            
                                                    }
                                            
                                                    @media (max-width: 767px) {
                                                        #myIframe {
                                                            width: 92%; /* Set full width for phones */
                                                        }
                                                    }
                                                `;
document.head.appendChild(styles);

// Create iframe element
// get the domain of the current page
var origin = window.location.origin;
console.log(origin);
var iframe = document.createElement('iframe');
iframe.id = 'myIframe';
iframe.src = "http://localhost:4200/chat-template";
iframe.style.borderRadius = "10px";
iframe.style.backgroundColor = "white";
iframe.scrolling = "no";
document.body.appendChild(iframe);

// Create toggle button element
var toggleButton = document.createElement('button');
toggleButton.id = 'toggleButton';
toggleButton.innerHTML = '';
toggleButton.style.position = 'fixed';
toggleButton.style.bottom = '10px';
toggleButton.style.right = '10px';
toggleButton.style.zIndex = '9999';
toggleButton.addEventListener('click', toggleIframe);
document.body.appendChild(toggleButton);

// Initial state: iframe is hidden
var iframeVisible = false;