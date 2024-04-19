const randomColor = () => {
    const randomColor = Math.floor(Math.random()*16777215).toString(16);
    return randomColor;
}
  

const createElement = (text) => {
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
        // add elemant
        span.appendChild(iconRemove);

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
        return span;
}

// nhap input tu giua ra
function limitToOneCharacterAndMoveCaret(input) {
    // Nếu độ dài của giá trị nhập vào là lớn hơn 1, cắt bớt
    if (input.value.length > 1) {
        input.value = input.value.slice(0, 1);
    }

    // Di chuyển con trỏ đến vị trí cuối cùng
    input.setSelectionRange(input.value.length, input.value.length);
}