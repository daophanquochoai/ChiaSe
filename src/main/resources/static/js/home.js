const uploadIcon = document.querySelector(".modal-body .container-image .upload_icon");
const inputUpload = document.querySelector(".modal-body .container-image #fileInput");
const box = document.querySelector(".modal-body .container-image .box");
const count = document.querySelector(".counter");
const boxTag = document.querySelector(".form-floating .box_tag");
const addBox = document.querySelector(".form-floating .add_tag");
const menuTag = document.querySelector(".form-floating .menu-tag");
const buttonAddTag = document.querySelector(".form-floating .add_tag .btn-add-tag");
const inputAddTag = document.querySelector(".form-floating .add_tag .input-add-tag");


var counter = 0;
uploadIcon.addEventListener("click", ()=>{
    inputUpload.click();
})
// upload image and render in display
inputUpload.addEventListener("change", ()=>{
    if(inputUpload.files.length > 0) {
        const selectedFile = inputUpload.files[0];
        const imageUrl = URL.createObjectURL(selectedFile);
        counter++;
        if( counter == 5){
            uploadIcon.style.display = "none";
        }
        // tao image
        const div = document.createElement("div");
        div.classList.add("image_upload");
        const image = document.createElement("img");
        image.classList.add("image_add");
        image.src = imageUrl;
        div.appendChild(image);
        const icon = document.createElement("i");
        icon.classList.add("fa-regular", "fa-circle-xmark", "icon_attach");
        icon.id = counter;
        const i = inputUpload.cloneNode(true);
        i.setAttribute("name","fileInput");
        i.style.display = "none";
        div.appendChild(i);
        count.innerHTML = counter;
        div.appendChild(icon);
        box.appendChild(div);
        // them action xoa
        const iconAttach = document.getElementById(counter);
        iconAttach.addEventListener("click", ()=>{
            box.removeChild(iconAttach.parentElement);
            if( counter == 5){
                uploadIcon.style.display = "inline-block";
            }
            counter--;
            count.innerHTML = counter;
        })
        //
    }
})

// add tag or use tag
const getTagOrAddTag = (e) => {
    if( e === "add" ){
        addBox.style.display = "flex";
    }
    else{
        // remove tag selected
        var li = Array.from(menuTag.children).filter( l => l.textContent.trim() === e);
        menuTag.removeChild(li[0]);
        // hide tag add
        addBox.style.display = "none";
        // create tag
        const span = document.createElement("span");
        span.style.outline = "1px solid " + "#" + randomColor();
        span.style.color = "#" + randomColor();
        span.classList.add("p-1");
        span.textContent = e;
        span.style.margin = "3px 5px";
        span.style.borderRadius = "5px";
        span.style.position = "relative";
        // create remove tag
        const iconRemove = document.createElement("i");
        iconRemove.classList.add("fa-solid", "fa-circle-xmark");
        iconRemove.style.color = "#" + randomColor();
        iconRemove.style.position = "absolute";
        iconRemove.style.font = "12px"
        iconRemove.style.top = "-6px";
        iconRemove.style.right = "-6px";
        iconRemove.style.cursor = "pointer";
        // luu gia tri upload
        const input = document.createElement("input");
        input.style.display = "none";
        input.value = e;
        input.setAttribute("name", "tags");
        span.appendChild(input);
        // add elemant
        span.appendChild(iconRemove);
        boxTag.appendChild(span);
        // action remove
        iconRemove.addEventListener("click", ()=>{
            boxTag.removeChild(iconRemove.parentElement);
            const li = document.createElement("li");
            const button = document.createElement("button");
            button.classList.add("dropdown-item");
            button.setAttribute("type", "button");
            button.onclick = function () {
                getTagOrAddTag(iconRemove.parentElement.textContent);
            };
            button.textContent = iconRemove.parentElement.textContent;
            li.appendChild(button);
            menuTag.appendChild(li);
        })
    }
}

// action add tag
buttonAddTag.addEventListener("click", ()=>{
    var text = inputAddTag.value;
    var check = Array.from(menuTag.children).filter( l => l.textContent.trim() === text);
    // check 
    if( check.length > 0 ){
        menuTag.removeChild(check[0]);
    }
    inputAddTag.value = "";
    // create tag
    const span = document.createElement("span");
    span.style.outline = "1px solid " + "#" + randomColor();
    span.style.color = "#" + randomColor();
    span.classList.add("p-1");
    span.textContent = text;
    span.style.margin = "3px 5px";
    span.style.borderRadius = "5px";
    span.style.position = "relative";
    // create remove tag
    const iconRemove = document.createElement("i");
    iconRemove.classList.add("fa-solid", "fa-circle-xmark");
    iconRemove.style.color = "#" + randomColor();
    iconRemove.style.position = "absolute";
    iconRemove.style.font = "12px"
    iconRemove.style.top = "-6px";
    iconRemove.style.right = "-6px";
    iconRemove.style.cursor = "pointer";
    // luu gia tri upload
    const input = document.createElement("input");
    input.style.display = "none";
    input.value = text;
    input.setAttribute("name", "tags");
    span.appendChild(input);
    // add element 
    span.appendChild(iconRemove);
    boxTag.appendChild(span);
    // action remove 
    iconRemove.addEventListener("click", ()=>{
        boxTag.removeChild(iconRemove.parentElement);
        const li = document.createElement("li");
        const button = document.createElement("button");
        button.classList.add("dropdown-item");
        button.setAttribute("type", "button");
        button.onclick = function () {
            getTagOrAddTag(iconRemove.parentElement.textContent);
        };
        button.textContent = iconRemove.parentElement.textContent;
        li.appendChild(button);
        menuTag.appendChild(li);
    })
})