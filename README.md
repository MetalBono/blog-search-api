블로그 검색 API by MetalBono
=======================================

개요
---------------------------------------
오픈 API 기반으로 블로그 내용을 검색하고, 인기검색어 정보를 집계 및 조회할 수 있는 Spring boot 웹 어플리케이션.

Java 17 버전에서 동작한다.

실행하기 (executable jar)
---------------------------------------
아래 경로에서 jar 파일 다운로드 
 * https://github.com/MetalBono/blog-search-api/raw/master/ext-files/blog-search-api-0.0.1.jar

커맨드로 실행하기
```
java -jar blog-search-api-0.0.1.jar
```

서버는 8080 포트에서 동작합니다.
 * http://localhost:8080

사용 라이브러리
---------------------------------------

|          라이브러리명 | 사용 이유                                                   |
|----------------:|:--------------------------------------------------------|
|          lombok | 코드 편의성 용도, 빌더, getter setter, 생성자 및 logger를 편리하게 사용 가능. |
|       openfeign | HTTP Client 용도, 인터페이스 명세 만으로 편하게 작성 가능.                 |
| cache, caffeine | 로컬 캐시 용도, 부하 방지를 위함.                                    |
|             aop | Aspecjt 적용 용도, Fallback 처리 시 활용을 위함.                    |
|       springdoc | Swagger UI 적용 용도, 테스팅 편의성 및 API 명세를 생성하는 용.             |

API 명세
---------------------------------------

아래 명세는 어플리케이션을 실행한 후, swagger-ui 접근을 통해서도 확인이 가능하다.
 * 접근 URL: http://localhost:8080/swagger-ui/index.html

### 1. 블로그 검색 API

Open API 를 이용하여 블로그 문서를 검색한다.

|          항목 | 내용                |
|------------:|:------------------|
|         URL | /api/blog/search  |
| HTTP Method | GET               |

#### Parameter

|      항목 | 내용                |
|--------:|:------------------|
|   query |검색어|
|    sort |정렬 기준|
|  source |검색 소스 (Kakao, Naver, ...)|
|    page |페이지 번호|
|    size |페이지 크기|


#### Response

|           속성 |     타입      | 설명                                |
|-------------:|:-----------:|:----------------------------------|
|   totalCount |   String    | 검색어에 해당하는 총 검색 결과 수            |
|      hasNext |   Boolean   | 다음 페이지 존재 여부                      |
|       source |   String    | 검색에 사용된 소스 정보 (Kakao, Naver, ...) |
| blogContents | BlogContent | 블로그 문서 내용. 하단의 blogContent 응답 참고  |

#### blogContents 응답 

|        속성 |     타입             | 설명                  |
|-----------:|:-------------------:|:--------------------|
|      title |       String        | 블로그 문서 제목           |
|   contents |       String        | 블로그 문서 내용           |
|    blogUrl |       String        | 블로그 주소 URL          |
|   blogName |       String        | 블로그 이름              |
|   postDate |       String        | 포스팅 날짜 (yyyy-MM-dd) |
|   metadata | Map<String, Object> | 추가 정보 (맵 형식)        |

#### 응답 예시
```
{
  "totalCount": 3262772,
  "hasNext": true,
  "source": "KAKAO",
  "blogContents": [
    {
      "title": "<b>티셔츠</b>",
      "contents": "물어볼라고 했는데... 지워졌네 ㅋㅋㅋ 돈되는 머리는 책방아재 방송보면 와 대단하다 저 사고방식 부럽다 싶었던 생각에서 나온 질문임 ㅋㅋㅋ 흠... 요즘 <b>티셔츠</b> 프린트그림을 보면 뭔가 힙하면서 아트한 아프리카 삘나는 그림이나 톡톡 튀는 현대미술느낌의 그림요소가 들어 가는거 같음... 디올 첫번째 <b>티</b>의 꽃...",
      "blogUrl": "http://ehfehfdl.tistory.com/314",
      "blogName": "B",
      "postDate": "2022-08-25",
      "metadata": {
        "thumbnail": "https://search1.kakaocdn.net/argon/130x130_85_c/Jp1rSm8XdZq"
      }
    },
    {
      "title": "맨투맨 <b>티셔츠</b> 추천",
      "contents": "맨투맨 <b>티셔츠</b> 추천 10대, 20대, 30대에서 많이 구매하고 있는 쇼핑 사이트 무신사 스토어에서 가장 인기 있는 맨투맨 아이템들만 모아봤습니다. 지금 구매 안 하면 구매 못하는 한정 판매 제품들이 있으니 참고해주세요!. 1. 소버먼트(SOVERMENT) 소버먼트(SOVERMENT) 제품 바로가기 소버먼트(SOVERMENT) 980g pigment...",
      "blogUrl": "http://frequency.tistory.com/402",
      "blogName": "부리왕국",
      "postDate": "2022-09-14",
      "metadata": {
        "thumbnail": "https://search3.kakaocdn.net/argon/130x130_85_c/5u17lWkDuPF"
      }
    },
    ...(중략)...
  ]
}
```


### 2. 인기검색어 조회 API

API를 통해 사용자들이 검색한 단어들 중 많이 조회된 순서로 상위 10개 검색어를 제공한다.

|          항목 | 내용                       |
|------------:|:-------------------------|
|         URL | /api/popular-words/top10 |
| HTTP Method | GET                      |


#### Parameter
 * 없음

#### Response

|      속성 |   타입   | 설명     |
|--------:|:------:|:-------|
| keyword | String | 검색된 단어  |
|   count |  Long  | 검색된 횟수 |

#### 응답 예시
```
[
  {
    "keyword": "소금빵",
    "count": 4
  },
  {
    "keyword": "티셔츠",
    "count": 2
  },
  {
    "keyword": "앙버터",
    "count": 1
  }
]
```
