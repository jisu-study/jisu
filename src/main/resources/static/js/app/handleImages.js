var imagesInput = document.getElementById('images');
var imagePreview = document.getElementById('imagePreview');
var prevButton = document.getElementById('prevButton');
var nextButton = document.getElementById('nextButton');
var slideContainer = document.querySelector('.slide-container');

var currentIndex = 0;

function showImage(index) {
    var images = slideContainer.getElementsByTagName('img');
    if (images.length > 0) {
        currentIndex = index;
        for (var i = 0; i < images.length; i++) {
            images[i].style.display = 'none';
        }
        images[currentIndex].style.display = 'block';
    }
}

imagesInput.addEventListener('change', function() {
    currentIndex = 0; // 파일 선택 시 초기화

    var files = imagesInput.files;

    // 이미지 미리보기 엘리먼트 초기화
    imagePreview.src = '';
    slideContainer.innerHTML = '';

    for (var i = 0; i < files.length; i++) {
        var file = files[i];
        var reader = new FileReader();

        reader.onload = function(event) {
            var img = document.createElement('img');
            img.src = event.target.result;

            slideContainer.appendChild(img);
        };

        reader.readAsDataURL(file);
    }

    showImage(currentIndex);
});

prevButton.addEventListener('click', function() {
    if (currentIndex > 0) {
        currentIndex--;
    }
    showImage(currentIndex);
});

nextButton.addEventListener('click', function() {
    if (currentIndex < slideContainer.children.length - 1) {
        currentIndex++;
    }
    showImage(currentIndex);
});

function saveImage() {
    if (imagesInput.files.length > 0) {
        var formData = new FormData();
        for (var i = 0; i < imagesInput.files.length; i++) {
            formData.append('image', imagesInput.files[i]);
        }

        /*fetch('/api/v1/plans', {
            method: 'POST',
            body: formData
        })
        .then(function(response) {
            return response.text();
        })
        .then(function(data) {
            // 업로드 성공한 경우의 처리
            console.log(data);
        })
        .catch(function(error) {
            // 오류 처리
            console.error('Error:', error);
        });*/

        return formData;
    }
}

export {saveImage};