
#include<stdio.h>
#include<stdlib.h>
#include <string.h>
#include <time.h>
#define MAXSIZE 20
#define MAX_INT 65535
//int map[MAXSIZE][MAXSIZE] = { MAX_INT };//存储路经的数组
typedef struct BUILDINFO//建筑信息
{
	int id;//建筑编号
	char name[20];//存储建筑名字的数组
	char info[256];//存储建筑信息结点的数组
}BuildInfo;

typedef struct BUILDNODE//建筑信息节点
{
	BuildInfo info;//建筑信息结点的结点信息
	struct BUILDNODE* next;//指向建筑信息结点的下一个结点（单链表）
}BuildNode;

typedef struct APPRAISEINFO//评价信息
{
	int id;//评价编号
	int source;//评分
	char info[256];
}AppraiseInfo;


typedef struct APPRAISENODE//评价节点
{
	AppraiseInfo info;
	struct APPRAISENODE* next;
}AppraiseNode;

typedef struct STACK//路径栈
{
	int buf[MAXSIZE];//存放的结点数
	int offset;//下标
}Stack;
typedef struct PATH//路径
{
	int weight;			//权重（路径长度）
	Stack previous;		//前进路径栈
	Stack backup;		//回溯路径栈
}Path;


//BuildNode* buildHead = NULL;
//Path dis[MAXSIZE];

BuildNode* CreateBuildHead()//建立建筑头节点
{
	BuildNode * head = (BuildNode*)malloc(sizeof(BuildNode));
	if (head != NULL)
	{
		head->next = NULL;
		return head;
	}
	return NULL;
}

AppraiseNode* CreateAppraiseHead()//建立评价头节点
{
	AppraiseNode * head = (AppraiseNode*)malloc(sizeof(AppraiseNode));
	if (head != NULL)
	{
		head->next = NULL;
		return head;
	}
	return NULL;
}

BuildNode* CreateBuildNode(BuildInfo info)//创建建筑节点
{
	BuildNode * node = (BuildNode*)malloc(sizeof(BuildNode));
	if (node == NULL) 
		return NULL;
	node->info = info;
	node->next = NULL;
	return node;
}

AppraiseNode* CreateAppraiseNode(AppraiseInfo info)//创建评价节点
{

	AppraiseNode* node = (AppraiseNode*)malloc(sizeof(AppraiseNode));
	if (node == NULL) 
		return NULL;
	node->info = info;
	node->next = NULL;
	return node;
}

int FindBuild(BuildNode* head,int id)//查找建筑根据节点编号
{
	BuildNode* node = head;
	if (node == NULL)return 0;
	while (node->next != NULL)
	{
		if (node->next->info.id == id)return 1;
		node = node->next;
	}
	return 0;
}

int InsertAppraise(AppraiseNode* head, AppraiseInfo info)//插入评价节点
{
	AppraiseNode* node = NULL;
	node = CreateAppraiseNode(info);
	if (node == NULL)return 0;
	node->next = head->next;
	head->next = node;
	return 1;
}

int InsertBuild(BuildNode* head, BuildInfo info)//插入建筑节点	//参数为建筑结点的头指针和建筑信息结点的信息
{
	BuildNode* node = NULL;	//建筑信息结点
	if (FindBuild(head, info.id) == 1)
		return 0;
	node = CreateBuildNode(info);
	if (node == NULL)
		return 0;
	node->next = head->next;
	head->next = node;
	return 1;
}


void BuildInit(BuildNode* head)//建筑初始化
{
	FILE* fp;//定义一个FILE类型的指针变量
	BuildInfo info;
	if (head == NULL)
	{
		return;
	}
	fp = fopen("build.txt", "r+");//用fopen函数引入事先写好的文件内容（r+表示以读写方式打开文件，既可以写入又可以读取）
	if (fp == NULL) 
		return;
	while (fscanf(fp, "%d%s%s", &info.id, info.name, info.info) != EOF)//当文件中无更多文件可读取时停止循环
	{
		InsertBuild(head, info);//插入建筑结点
	}
	fclose(fp);//关闭文件及释放相关资源（避免数据丢失）
}

void RoadInit(int map[MAXSIZE][MAXSIZE])//路径初始化
{
	int start, end,far;
	FILE* fp = fp = fopen("far.txt", "r+");
	if (fp == NULL) return;
	while (fscanf(fp, "%d%d%d", &start, &end, &far) != EOF)		//当文件中无更多文件可读取时停止循环(文件中的三个数值分别为：起始点建筑编号，目的地建筑编号，路径长度)
	{
		if (start < 0 || start >= MAXSIZE || end<0 || end>=MAXSIZE || far < 0)
		{
		
		}
		else
		{
			map[start][end] = far;
			map[end][start] = far;
		}
	}
}

void AppraiseInit(AppraiseNode* head)//评价初始化
{
	FILE* fp;
	AppraiseInfo info;
	fp = fopen("appraise.txt", "r+");
	if (fp == NULL) return;
	while (fscanf(fp, "%d%d%s", &info.id, &info.source, info.info) != EOF)
	{
		InsertAppraise(head, info);
	}
	fclose(fp);
}

void AppendAppraise(AppraiseInfo info)//追加评价
{
	FILE* fp;
	fp = fopen("appraise.txt", "a+");//打开文件appraise并允许追加写入文件
	if (fp == NULL) return;
	fprintf(fp, "%d\t%d\t%s\n", info.id, info.source, info.info);//写入文件
	fclose(fp);
}

void forMatRoad(int map[MAXSIZE][MAXSIZE])//格式化路径
{
	int i = 0;
	int j = 0;
	for (i = 0; i < MAXSIZE; i++)
	{
		for (j = 0; j < MAXSIZE; j++)
		{
			map[i][j] = MAX_INT;
		}
	}
}

void clearStack(Stack* s)	//清除已经遍历的路径
{
	int i = 0;
	for (i = 0; i < MAXSIZE; i++)
	{
		s->buf[i] = -1;
	}
	s->offset = 0;
}

/*
* map 道路长度临界表
* dis 每个节点通往下一节点的路径
* x 开始位置
* y 结束位置
* sum 路径长度
* p 开始地址到结束节点的经过的路径，总结起来就是找到每一个节点和相邻节点的最短距离然后计算后确定最短路径节点入栈
* c p的下表
*/


//DFS深度优先遍历算法
void dfs(int map[MAXSIZE][MAXSIZE],Path dis[MAXSIZE],int x, int y, int* sum, int* p, int* c)
{
	if (dis[y].previous.offset == 0)	//前驱搜索完把开始节点和结束结点入栈
	{
		(*sum) += map[x][y];
		p[(*c)++] = x;
		p[(*c)++] = y;
	}
	// 将节点所有分支搜索完
	while (dis[y].previous.offset > 0)//前驱搜索
	{
		dfs(map,dis,x, dis[y].previous.buf[dis[y].previous.offset - 1], sum, p, c);//递归计算x-前驱的最短路径	// dis[y].previous.buf[dis[y].previous.offset - 1]即为此刻终点的上一个结点（不断向前递归求得从x开始到该终点的路径长度）
		(*sum) += map[dis[y].previous.buf[dis[y].previous.offset - 1]][y];//计算路径距离			//sum=sum+map[当前结点的终点y的前驱节点][y]
		p[(*c)++] = y;
		dis[y].backup.buf[dis[y].backup.offset] = dis[y].previous.buf[dis[y].previous.offset - 1];//前驱结点放入回溯
		dis[y].backup.offset++;
		dis[y].previous.buf[dis[y].previous.offset - 1] = -1;	//前驱置空
		dis[y].previous.offset--;//回退
	}
	// 将前驱信息还原
	while (dis[y].previous.offset > 0)
	{
		dis[y].previous.buf[dis[y].previous.offset] = dis[y].backup.buf[dis[y].backup.offset - 1];//回溯结点放回前驱
		dis[y].previous.offset++;
		dis[y].backup.buf[dis[y].backup.offset - 1] = -1;
		dis[y].backup.offset--;
	}
}

/*
* map 道路长度临界表
* dis 每个节点通往下一节点的路径
* x 开始位置
* y 结束位置
* sum 路径长度
* p 开始地址到结束节点的经过的路径，总结起来就是找到每一个节点和相邻节点的最短距离然后计算后确定最短路径节点入栈
* c p的下表
*/




void getPath(int map[MAXSIZE][MAXSIZE], Path dis[MAXSIZE],int x, int y, int* sum, int* p)//获得所有结点的路径的可能性(任何一个点到另外一个点的路径)		//int map[][]获取路径的数组（存放每个点之间的距离）
{																																						//x，y为已输入的出发地和目的地的建筑编号
	int i;
	int count;
	int rest[MAXSIZE] = { 0 };//rest[]用来标志是否遍历过，如果遍历过则为1，没遍历过则为0
	int c = 0;
	rest[x] = 1;
	// 初始化路径权值
	for (i = 0; i < MAXSIZE; i++)
	{
		dis[i].weight = map[x][i];	//此结点通往下一个结点的路径权重
		clearStack(&dis[i].previous);//让previous和backup的buff[i]=-1;offset=0（清除已经遍历的路径）			
		clearStack(&dis[i].backup);
	}

	for (count = 1; count != MAXSIZE; count++)
	{
		// 寻找权值最小顶点
		int min = MAX_INT;
		for (i = 0; i < MAXSIZE; i++)
			if (rest[i] == 0)//如果没有遍历过
			{
				min = i;//没被遍历过则此时的i结点就是权值最小结点
				for (i += 1; i < MAXSIZE; i++)
					if (rest[i] == 0 && dis[min].weight > dis[i].weight)//没有遍历过且权重小
						min = i;
			}
		if (min == MAX_INT)//无路可走
			return;
		else
			rest[min] = 1;

		// 更新路径权值
		for (i = 0; i < MAXSIZE; i++)
		{
			if (rest[i] == 1)
				continue;	//如果该节点已经遍历过了，结束本次循环
			if (map[min][i] <= dis[i].weight - dis[min].weight)//判断每一结点的最短路径
			{
				if (map[min][i] != dis[i].weight - dis[min].weight)//如果最短路径不相等		（如果存放的两结点之间的最短路径不等于从起点到i点的路径长度减去起点到本个点的最小长度）
					clearStack(&dis[i].previous);//清除此点的前驱
				dis[i].weight = dis[min].weight + map[min][i];
				dis[i].previous.buf[dis[i].previous.offset] = min;
				dis[i].previous.offset++;
			}
		}
		rest[min] = 1;//标志此点为已遍历过
	}

	// 递归输出最短路径

	dfs(map,dis,x, y, sum, p, &c);
}

int getBuildId(BuildNode*head,char* name)//获取建筑编号
{
	BuildNode* end;
	if (head == NULL)
		return -1;
	end = head;
	while (end->next != NULL)
	{
		if (strcmp(end->next->info.name, name) == 0)
		{
			return end->next->info.id;//查找成功，返回该建筑的编号
		}
		end = end->next;
	}
	return -1;//查找获取建筑编号失败
}

void outputRoad(BuildNode* head,int* p)//打印路径
{
	int* a;
	int s = -1;
	BuildNode* end;
	a = p;
	printf("路径：");
	while (*a > 0)
	{
		end = head;
		while (end->next != NULL)
		{
			if (*a == end->next->info.id)
			{
				printf("%s", end->next->info.name);
			}
			end = end->next;
		}
		if (*(++a) > 0)
		{
			printf("-->");
		}
		else
		{
			printf("\n");
		}

	}
}

void formatPlace(int* p)//格式化路径
{
	int i = 0;
	for (i = 0; i < MAXSIZE; i++)
	{
		p[i] = -1;//将数组p[]的所有值都赋值为-1
	}
}

void IssuanceAppraise(AppraiseNode * head)//发表评价
{
	AppraiseInfo info;//评价信息结点
	time_t t;
	printf("请输入满意度(0:非常不满 - 10:非常满意):");
	scanf("%d", &info.source);
	if (info.source < 0 || info.source>10)
	{
		printf("满意度应在0-10之间\n");
		return;
	}
	printf("请输入使用评价:");
	scanf("%s", info.info);
	t = time(NULL);//截取到当前时间
	info.id = t;//以评价时间作为评价结点的id
	AppendAppraise(info);//将该评价写入文件
	InsertAppraise(head, info);//插入评价结点
	printf("评价发表成功\n");
}

void PrintfBuildInfoByName(BuildNode* head,char* name)//根据名称查询建筑
{
	BuildNode* end;
	if (head == NULL)return;
	end = head;
	while (end->next != NULL)	//一直查找直到建筑结点的下一个指针域为空或者找到了符合的要求的字符串
	{
		if (strcmp(end->next->info.name, name) == 0)	//以建筑名字为查找关键字，运用strcmp函数（比较字符串），字符串相同返回值为0
		{
			printf("景点编号:%d\n景点名称:%s\n景点介绍:%s\n", end->next->info.id, end->next->info.name, end->next->info.info);
			return;
		}
		end = end->next;//查找不到继续到下一个建筑结点找
	}
}

void PrintfBuildInfo(BuildNode* head)//浏览建筑列表
{
	BuildNode* end;
	if (head == NULL)
		return;
	end = head;	//将head的地址赋值给指针变量end
	while (end->next != NULL)//头结点的next结点不为空时进行打印，直到下一个结点为空（即没有了建筑结点）
	{
		printf("景点编号:%d\n景点名称:%s\n景点介绍:%s\n", end->next->info.id, end->next->info.name, end->next->info.info);
		end = end->next;//移动到下一个建筑结点
	}
}

void PrintfAppraiseInfoByLevel(AppraiseNode* head, int level)//根据满意度查询评价
{
	AppraiseNode* end;
	char dateTime[20] = { 0 };
	time_t t;
	int i;
	if (head == NULL)
		return;
	end = head;
	while (end->next != NULL)
	{
		if (end->next->info.source == level)//评价结点的对应编号等级和输入的等级匹配
		{
			t = end->next->info.id;
			struct tm* tt = localtime(&t);
			printf("评价时间:%04d-%02d-%02d %02d:%02d:%02d\n", tt->tm_year + 1900, tt->tm_mon + 1, tt->tm_mday, tt->tm_hour, tt->tm_min, tt->tm_sec);
			printf("评价等级:");
			for (i = 0; i < level;i++)
			{
				printf("★");
			}
			printf("\n%s\n", end->next->info.info);
		}
		end = end->next;
	}
}

void PrintfAppraiseInfo(AppraiseNode* head)//打印评价
{
	AppraiseNode* end;
	char dateTime[20] = { 0 };
	time_t t;//time_t时间类型
	int i;
	if (head == NULL)//没有评价直接返回结束
		return;
	end = head;
	while (end->next != NULL)
	{
		t = end->next->info.id;
		struct tm* tt = localtime(&t);
		printf("评价时间:%04d-%02d-%02d %02d:%02d:%02d\n", tt->tm_year + 1900, tt->tm_mon + 1, tt->tm_mday, tt->tm_hour, tt->tm_min, tt->tm_sec);//年月日分秒
		printf("评价等级:");
		for (i = 0; i < end->next->info.source; i++)//评分为几就打印几颗星
		{
			printf("★");
		}
		printf("\n%s\n", end->next->info.info);//打印评价结点的评价内容
		end = end->next;
	}
}


int MainMenu()
{
	int choise;
	printf("==============欢迎使用柏荪导航==============\n");
	printf("********************************************\n");
	printf("*             1.浏览景点信息               *\n");
	printf("*             2.查询景点信息               *\n");
	printf("*             3.景点导航                   *\n");
	printf("*             4.评价系统                   *\n");
	printf("*             5.浏览系统评价               *\n");
	printf("*             6.查询系统评价               *\n");
	printf("*             0.退出系统                   *\n");
	printf("********************************************\n");
	printf("请根据菜单操作:");
	scanf("%d", &choise);
	return choise;
}

int main()
{
	int map[MAXSIZE][MAXSIZE] = { MAX_INT };	//存储路径的数组
	BuildNode* buildHead = NULL;				//建筑信息结点
	AppraiseNode* appraiseHead = NULL;			//评价结点			
	Path dis[MAXSIZE];							//路径数组
	char start[20] = { 0 };						//出发地
	char end[20] = { 0 };						//目的地
	int sum = 0;								//路径长度（初始化为0）
	int p[MAXSIZE] = { -1 };					//格式化路径的参数（初始化为-1）
	int x = -1;
	int y = -1;
	int chooise;
	int level;									//评价等级
	char name[20] = { 0 };
	buildHead = CreateBuildHead();				//创建建筑头结点（case1中函数的参数）
	appraiseHead = CreateAppraiseHead();		//评价头结点
	formatPlace(p);								//格式化路径数组p[](p数组所有值设为-1)
	forMatRoad(map);							//格式化结点间距离数组map[][]	（map数组所有值设为正无穷）
	BuildInit(buildHead);//初始化建筑结点（把写好的文件数据放到建筑结点中）
	AppraiseInit(appraiseHead);					//初始化评价结点
	RoadInit(map);								//路径初始化（导入far文件的数据到map[][]）
	while (true)
	{
		system("cls");//清屏
		chooise = MainMenu();//菜单函数
		switch (chooise)//用设置的菜单函数进行选择
		{
		case 0:break;	//输入0直接结束程序
		case 1:			
		{
			PrintfBuildInfo(buildHead);//浏览建筑列表(参数为建筑信息头结点的地址)
		}break;
		case 2:
		{
			printf("请输入查询的景点名:");
			scanf("%s", name);
			PrintfBuildInfoByName(buildHead, name);//根据名称查询建筑
		}break;
		case 3:
		{
			printf("请输入出发地:");
			memset(start, 0, 20);
			scanf("%s", start);
			x = getBuildId(buildHead, start);//用getBuildId函数获取到出发地的建筑编号，赋值给x
			if (x > 0)//出发地建筑存在
			{
				printf("请输入目的地:");
				memset(end, 0, 20);//用memset()函数初始化目的地end
				scanf("%s", end);
				y = getBuildId(buildHead, end);//获得目的地的建筑编号，赋值给y
				if (y > 0)//目的地存在
				{
					formatPlace(p);//格式化路径（将数组p[]的所有值都赋值为-1）
					getPath(map, dis, x, y, &sum, p);//获取路径
					outputRoad(buildHead, p);//输出路径
					printf("距离:%d\n", sum);
				}
				else
				{
					printf("目的地输入有误!\n");
				}
			}
			else
			{
				printf("出发地输入有误!\n");
			}
		}break;
		case 4:
		{
			IssuanceAppraise(appraiseHead);//发表评价
		}break;
		case 5:
		{
			PrintfAppraiseInfo(appraiseHead);//打印评价
		}break;
		case 6:
		{
			printf("请输入查看的评价等级(0-10):");
			scanf("%d", &level);
			PrintfAppraiseInfoByLevel(appraiseHead,level);//以评价等级为基准打印对应等级的所有评价
		}break;
		default:
			printf("操作有误");
			break;
		}
		if (chooise == 0)//没输入数字按任意键也可以返回
			break;
		system("pause");//使程序暂停，按任意键继续
	}
	system("pause");
	return 0;
}